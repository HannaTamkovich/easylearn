package com.easylearn.easylearn.security.user.web.controller;

import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.dto.UpdateUserParam;
import com.easylearn.easylearn.security.user.service.UserService;
import com.easylearn.easylearn.security.user.web.converter.UserWebConverter;
import com.easylearn.easylearn.security.user.web.dto.UserPageResponse;
import com.easylearn.easylearn.security.user.web.dto.UserResponse;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.Collection;

@RestController
@RequestMapping(UserController.BASE_PATH)
@Validated
@AllArgsConstructor
@CrossOrigin
public class UserController {

    //TODO восстановление аккаунта
    //TODO забыл пароль

    public static final String BASE_PATH = "/users";
    public static final String ME_PATH = "/me";
    public static final String USERNAME_PATH = "/{username}";

    private final UserService userService;
    private final UserWebConverter userWebConverter;
    private final CurrentUserService currentUserService;

    @GetMapping
    public Collection<UserPageResponse> findAll(@Nullable @PathParam("search") String search) {
        var currentUserUsername = currentUserService.getUsername();
        var users = userService.findAll(currentUserUsername, search);
        return userWebConverter.toUsersPageResponses(users);
    }

    @GetMapping(ME_PATH)
    public UserResponse me() {
        var user = userService.loadByUsername(currentUserService.getUsername());
        return userWebConverter.toResponse(user);
    }

    @GetMapping(USERNAME_PATH)
    public UserResponse read(@NotNull @PathVariable String username) {
        var user = userService.loadByUsername(username);
        return userWebConverter.toResponse(user);
    }

    @PutMapping(USERNAME_PATH)
    public void update(@NotNull @PathVariable String username, @Valid @NotNull @RequestBody UpdateUserParam updateUserParam) {
        userService.update(username, updateUserParam);
    }

    @DeleteMapping(USERNAME_PATH)
    public void delete(@NotBlank @PathVariable String username) {
        userService.delete(username);
    }
}
