package com.easylearn.easylearn.security.user.service;

import com.easylearn.easylearn.core.exception.DuplicateException;
import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import com.easylearn.easylearn.security.user.repository.converter.UserEntityConverter;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import com.easylearn.easylearn.security.user.service.converter.UserParamConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
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

    @Transactional(readOnly = true)
    @Override
    @NotNull
    public Optional<User> findByUsernameAndActiveStatus(@NotNull String username) {
        log.debug("Reading user: '{}'", username);
        var userEntityOpt = userRepository.findByUsernameAndDeletedFalse(username);
        return userEntityOpt.map(userEntity -> {
            var userAccount = userEntityConverter.toModel(userEntity);
            log.debug("User has been read: '{}'", userAccount);
            return userAccount;
        });
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public User loadByUsername(@NotNull String username) {
        log.debug("Reading user: '{}'", username);
        var userEntity = loadUserEntity(username);
        var userAccount = userEntityConverter.toModel(userEntity);
        log.debug("User has been read: '{}'", userAccount);
        return userAccount;
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
        var userAccount = userEntityConverter.toModel(userEntity);
        log.debug("User has been read: '{}'", userAccount);
        return userAccount;
    }

    @Override
    @Transactional
    public void create(@NotNull @Valid BaseUserParam baseUserParam) {
        log.info("Creating new user");

        validateUserByUsername(baseUserParam);

        var userAccount = userParamConverter.toModel(baseUserParam);
        userAccount.setDateOfLastVisit(Instant.now());
        userRepository.save(userEntityConverter.toEntity(userAccount));

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
    public void update(@NotBlank String username, @NotNull @Valid BaseUserParam baseUserParam) {
        //TODO implementation
    }

    @Override
    @Transactional
    public void delete(@NotBlank String username) {
        log.info("Deleting user");

        var userAccount = loadByUsername(username);

        userAccount.setDeleted(true);
        userRepository.save(userEntityConverter.toEntity(userAccount));

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
            throw new DuplicateException(format("Логин ('%s') занят. Необходимо ввести другой логин", baseUserParam.getUsername()));
        }
    }
}
