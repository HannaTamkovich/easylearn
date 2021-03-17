package com.easylearn.easylearn.security.user.service;

import com.easylearn.easylearn.security.user.dto.BaseUserAccountParam;
import com.easylearn.easylearn.security.user.dto.DeleteUserAccountParam;
import com.easylearn.easylearn.security.user.model.UserAccount;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface UserAccountService {

    @NotNull
    UserAccount loadByUsername(@NotNull String username);

    boolean existsByUsername(@NotNull String username);

    @NotNull
    Optional<UserAccount> findByUsernameAndActiveStatus(@NotNull String username);

    @NotNull
    UserAccount loadById(@NotNull Long id);

    void create(@NotNull @Valid BaseUserAccountParam baseUserAccountParam);

    @NotNull
    Collection<UserAccount> findAll();

    void update(@NotNull @Valid BaseUserAccountParam baseUserAccountParam);

    void delete(@NotNull DeleteUserAccountParam deleteUserAccountParam);
}
