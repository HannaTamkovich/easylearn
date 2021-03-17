package com.easylearn.easylearn.security.user.service;

import com.easylearn.easylearn.core.exception.DuplicateException;
import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.security.user.dto.BaseUserAccountParam;
import com.easylearn.easylearn.security.user.dto.DeleteUserAccountParam;
import com.easylearn.easylearn.security.user.model.UserAccount;
import com.easylearn.easylearn.security.user.repository.UserAccountRepository;
import com.easylearn.easylearn.security.user.repository.converter.UserAccountEntityConverter;
import com.easylearn.easylearn.security.user.repository.entity.UserAccountEntity;
import com.easylearn.easylearn.security.user.service.converter.UserAccountParamConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountEntityConverter userAccountEntityConverter;
    private final UserAccountParamConverter userAccountParamConverter;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    @NotNull
    public Optional<UserAccount> findByUsernameAndActiveStatus(@NotNull String username) {
        log.debug("Reading user: '{}'", username);
        var userEntityOpt = userAccountRepository.findByUsernameAndDeletedFalse(username);
        return userEntityOpt.map(userEntity -> {
            var userAccount = userAccountEntityConverter.toModel(userEntity);
            log.debug("User has been read: '{}'", userAccount);
            return userAccount;
        });
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public UserAccount loadByUsername(@NotNull String username) {
        log.debug("Reading user: '{}'", username);
        var userEntity = loadUserAccount(username);
        var userAccount = userAccountEntityConverter.toModel(userEntity);
        log.debug("User has been read: '{}'", userAccount);
        return userAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(@NotNull String username) {
        return userAccountRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    @NotNull
    @Override
    public UserAccount loadById(@NotNull Long id) {
        log.debug("Reading user: '{}'", id);
        var userEntity = userAccountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User account with id '{}' not found", id);
                    throw new EntityNotFoundException(UserAccountEntity.class.getName(), id);
                });
        var userAccount = userAccountEntityConverter.toModel(userEntity);
        log.debug("User has been read: '{}'", userAccount);
        return userAccount;
    }

    @Override
    @Transactional
    public void create(@NotNull @Valid BaseUserAccountParam baseUserAccountParam) {
        log.info("Creating new user");

        validateUserByUsername(baseUserAccountParam);

        var userAccount = userAccountParamConverter.toModel(baseUserAccountParam);
        userAccount.setDateOfLastVisit(Instant.now());
        userAccountRepository.save(userAccountEntityConverter.toEntity(userAccount));

        log.debug("User has been created");
    }

    @Override
    @NotNull
    @Transactional(readOnly = true)
    public Collection<UserAccount> findAll() {
        return userAccountEntityConverter.toModels(userAccountRepository.findAll());
    }

    @Override
    @Transactional
    public void update(@NotNull @Valid BaseUserAccountParam baseUserAccountParam) {
        //TODO implementation
    }

    @Override
    @Transactional
    public void delete(@NotNull DeleteUserAccountParam deleteUserAccountParam) {
        log.info("Deleting user");

        var userAccount = loadById(deleteUserAccountParam.getId());
        validatePassword(userAccount, deleteUserAccountParam.getPassword());

        userAccount.setDeleted(true);
        userAccountRepository.save(userAccountEntityConverter.toEntity(userAccount));

        log.debug("User has been deleted");
    }

    private UserAccountEntity loadUserAccount(String username) {
        return userAccountRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User account '{}' not found", username);
                    throw new EntityNotFoundException(UserAccountEntity.class.getName());
                });
    }

    private void validateUserByUsername(BaseUserAccountParam baseUserAccountParam) {
        if (existsByUsername(baseUserAccountParam.getUsername())) {
            throw new DuplicateException(format("Логин ('%s') занят. Необходимо ввести другой логин", baseUserAccountParam.getUsername()));
        }
    }

    private void validatePassword(UserAccount userAccount, String password) {
        if (!passwordEncoder.matches(password, userAccount.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }
    }
}
