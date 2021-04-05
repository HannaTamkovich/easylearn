package com.easylearn.easylearn.security.user.service;

import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.dto.UpdateUserParam;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import com.sun.istack.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

public interface UserService {

    @NotNull
    User loadByUsername(@NotNull String username);

    @NotNull
    UserEntity loadUserEntity(@NotNull String username);

    @NotNull
    Optional<User> findByUsernameAndActiveStatus(@NotNull String username);

    @NotNull
    Collection<User> findAll(@NotNull String currentUserUsername, @Nullable String search);

    void create(@NotNull @Valid BaseUserParam baseUserParam);

    void update(@NotBlank String username, @NotNull @Valid UpdateUserParam updateUserParam);

    void delete(@NotBlank String username);

    void login(@NotNull @Valid User user);
}
