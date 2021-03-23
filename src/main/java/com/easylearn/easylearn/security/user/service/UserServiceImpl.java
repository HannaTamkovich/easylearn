package com.easylearn.easylearn.security.user.service;

import com.easylearn.easylearn.core.exception.DuplicateException;
import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.dto.UpdateUserParam;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import com.easylearn.easylearn.security.user.repository.converter.UserEntityConverter;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import com.easylearn.easylearn.security.user.service.converter.UserParamConverter;
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

    @Transactional(readOnly = true)
    @Override
    @NotNull
    public Optional<User> findByUsernameAndActiveStatus(@NotNull String username) {
        log.debug("Reading user: '{}'", username);
        var userEntityOpt = userRepository.findByUsernameAndDeletedFalse(username);
        return userEntityOpt.map(userEntity -> {
            var user = userEntityConverter.toModel(userEntity);
            log.debug("User has been read: '{}'", user);
            return user;
        });
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public User loadByUsername(@NotNull String username) {
        log.debug("Reading user: '{}'", username);
        var userEntity = loadUserEntity(username);
        var user = userEntityConverter.toModel(userEntity);
        log.debug("User has been read: '{}'", user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(@NotNull String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public User loadById(@NotNull Long id) {
        log.debug("Reading user: '{}'", id);
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User account with id '{}' not found", id);
                    throw new EntityNotFoundException(UserEntity.class.getName(), id);
                });
        var user = userEntityConverter.toModel(userEntity);
        log.debug("User has been read: '{}'", user);
        return user;
    }

    @Override
    @Transactional
    public void create(@NotNull @Valid BaseUserParam baseUserParam) {
        log.info("Creating new user");

        validateUserByUsername(baseUserParam);

        var user = userParamConverter.toModel(baseUserParam);
        user.setDateOfLastVisit(Instant.now());
        userRepository.save(userEntityConverter.toEntity(user));

        log.debug("User has been created");
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public Collection<User> findAll() {
        return userEntityConverter.toModels(userRepository.findAll());
    }

    @Override
    @Transactional
    public void update(@NotBlank String username, @NotNull @Valid UpdateUserParam updateUserParam) {
        log.info("Update user");

        var user = loadByUsername(username);

        validatePassword(user, updateUserParam);
        if (!StringUtils.equals(username, updateUserParam.getUsername())) {
            validateUserByUsername(updateUserParam);
        }

        var newUser = userParamConverter.toModel(updateUserParam);
        var newPassword = StringUtils.isNotBlank(updateUserParam.getNewPassword()) ? passwordEncoder.encode(updateUserParam.getNewPassword()) : user.getPassword();
        newUser.setPassword(newPassword);

        userRepository.save(userEntityConverter.toEntity(newUser));

        log.debug("User has been updated");
    }

    private void validatePassword(User user, UpdateUserParam updateUserParam) {
        if (StringUtils.isBlank(updateUserParam.getPassword()) || !passwordEncoder.matches(updateUserParam.getPassword(), user.getPassword())) {
            throw new ValidationException("Неверный пароль");
        }
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

    private UserEntity loadUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User account '{}' not found", username);
                    throw new EntityNotFoundException(UserEntity.class.getName());
                });
    }

    private void validateUserByUsername(BaseUserParam baseUserParam) {
        if (existsByUsername(baseUserParam.getUsername())) {
            throw new DuplicateException(format("Логин ('%s') занят. Введите другой", baseUserParam.getUsername()));
        }
    }
}
