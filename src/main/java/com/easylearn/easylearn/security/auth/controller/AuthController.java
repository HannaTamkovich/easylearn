package com.easylearn.easylearn.security.auth.controller;

import com.easylearn.easylearn.security.auth.dto.LoginParam;
import com.easylearn.easylearn.security.auth.service.TokenStoreService;
import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.service.UserService;
import com.easylearn.easylearn.security.useractivation.service.UserActivationService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin
public class AuthController {

    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";
    public static final String SIGN_UP_PATH = "/sign-up";
    public static final String ACTIVATE_PATH = "/activate/{username}";

    private final TokenStoreService tokenStoreService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final UserActivationService userActivationService;

    @PostMapping(LOGIN_PATH)
    public String login(@Valid @NotNull @RequestBody LoginParam loginParam) {
        return tokenStoreService.create(loginParam);
    }

    @PostMapping(LOGOUT_PATH)
    public void logout(@NotNull @RequestHeader("Authorization") String token) {
        tokenStoreService.remove(token);
    }

    @PostMapping(SIGN_UP_PATH)
    public void createUser(@Valid @NotNull @RequestBody BaseUserParam baseUserParam) {
        baseUserParam.setPassword(passwordEncoder.encode(baseUserParam.getPassword()));
        userService.create(baseUserParam);
    }

    @GetMapping(ACTIVATE_PATH)
    public RedirectView activate(@NotNull @PathVariable String username) {
        userActivationService.activate(username);
        return new RedirectView("http://localhost:3000/login");
    }
}
