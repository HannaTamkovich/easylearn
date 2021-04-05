package com.easylearn.easylearn.security.user.service;

import com.easylearn.easylearn.core.exception.DuplicateException;
import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.email.MailSenderService;
import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.dto.UpdateUserParam;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import com.easylearn.easylearn.security.user.repository.converter.UserEntityConverter;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import com.easylearn.easylearn.security.user.service.converter.UserParamConverter;
import com.easylearn.easylearn.security.useractivation.service.UserActivationService;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserEntityConverter userEntityConverter;
    private final UserParamConverter userParamConverter;

    private final PasswordEncoder passwordEncoder;

    private final UserActivationService userActivationService;
    private final MailSenderService mailSenderService;

    @Transactional(readOnly = true)
    @Override
    @NotNull
    public Optional<User> findByUsernameAndActiveStatus(@NotNull String username) {
        log.debug("Find user by username: '{}'", username);

        return userRepository.findByUsernameAndDeletedFalse(username)
                .map(userEntityConverter::toModel);
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public User loadByUsername(@NotNull String username) {
        log.debug("Load user by username: '{}'", username);

        var userEntity = loadUserEntity(username);
        return userEntityConverter.toModel(userEntity);
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public UserEntity loadUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(UserEntity.class.getName()));
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public Collection<User> findAll(@NotNull String currentUserUsername, @Nullable String search) {
        log.info("Find user list");

        var language = loadByUsername(currentUserUsername).getLanguage();
        var userEntities = Optional.ofNullable(search)
                .map(it -> userRepository.findByUsernameNotAndLanguageAndUsernameContainsOrderByUsername(currentUserUsername, language, it))
                .orElse(userRepository.findByUsernameNotAndLanguageOrderByUsername(currentUserUsername, language));

        return userEntityConverter.toModels(userEntities);
    }

    @Override
    @Transactional
    public void create(@NotNull @Valid BaseUserParam baseUserParam) {
        log.info("Creating new user");

        validateUserByUsername(baseUserParam);
        validateEmail(baseUserParam.getEmail());

        var userToSave = userParamConverter.toModel(baseUserParam);
        userToSave.setDateOfLastVisit(Instant.now());
        var savedUser = userRepository.save(userEntityConverter.toEntity(userToSave));

        var user = userEntityConverter.toModel(savedUser);
        userActivationService.generateCode(user);
        mailSenderService.sendVerificationMessage(user);

        log.debug("User has been created");
    }

    @Override
    @Transactional
    public void update(@NotBlank String username, @NotNull @Valid UpdateUserParam updateUserParam) {
        log.info("Update user");

        var user = loadByUsername(username);
        var email = user.getEmail();

        validatePassword(user, updateUserParam);
        validateEmailToUpdate(updateUserParam, email);

        userParamConverter.toUpdatedModel(updateUserParam, user);
        updatePassword(updateUserParam, user);

        var savedUserEntity = userRepository.save(userEntityConverter.toEntity(user));

        if (!StringUtils.equals(email, updateUserParam.getEmail())) {
            var savedUser = userEntityConverter.toModel(savedUserEntity);

            userActivationService.generateCode(savedUser);
            mailSenderService.sendVerificationMessage(savedUser);
        }

        log.debug("User has been updated");
    }

    @Override
    @Transactional
    public void delete(@NotBlank String username) {
        log.info("Deleting user");

        var user = loadByUsername(username);

        user.setDeleted(true);
        userRepository.save(userEntityConverter.toEntity(user));

        log.debug("User has been deleted");
    }

    @Override
    @Transactional
    public void login(@NotNull @Valid User user) {
        log.info("Set login date");

        user.setDateOfLastVisit(Instant.now());
        userRepository.save(userEntityConverter.toEntity(user));
    }

    private void validateUserByUsername(BaseUserParam baseUserParam) {
        if (userRepository.existsByUsername(baseUserParam.getUsername())) {
            throw new DuplicateException(format("Логин ('%s') занят. Введите другой", baseUserParam.getUsername()));
        }
    }

    private void validatePassword(User user, UpdateUserParam updateUserParam) {
        if (StringUtils.isBlank(updateUserParam.getPassword()) || !passwordEncoder.matches(updateUserParam.getPassword(), user.getPassword())) {
            throw new ValidationException("Неверный пароль");
        }
    }

    private void updatePassword(UpdateUserParam updateUserParam, User user) {
        var newPassword = StringUtils.isNotBlank(updateUserParam.getNewPassword()) ?
                passwordEncoder.encode(updateUserParam.getNewPassword()) : passwordEncoder.encode(user.getPassword());
        user.setPassword(newPassword);
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateException(format("Пользователь с почтной '%s' уже существует", email));
        }
    }

    private void validateEmailToUpdate(UpdateUserParam updateUserParam, String email) {
        if (!StringUtils.equals(email, updateUserParam.getEmail())) {
            validateEmail(updateUserParam.getEmail());
        }
    }
}
