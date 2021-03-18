package com.easylearn.easylearn.security.user.service;

import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.dto.DeleteUserParam;
import com.easylearn.easylearn.security.user.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface UserService {

    @NotNull
    User loadByUsername(@NotNull String username);

    boolean existsByUsername(@NotNull String username);

    @NotNull
    Optional<User> findByUsernameAndActiveStatus(@NotNull String username);

    @NotNull
    User loadById(@NotNull Long id);

    void create(@NotNull @Valid BaseUserParam baseUserParam);

    @NotNull
    Collection<User> findAll();

    void update(@NotNull @Valid BaseUserParam baseUserParam);

    void delete(@NotNull DeleteUserParam deleteUserParam);
}
