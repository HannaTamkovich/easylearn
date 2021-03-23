package com.easylearn.easylearn.security.auth.controller;

import com.easylearn.easylearn.security.auth.dto.LoginParam;
import com.easylearn.easylearn.security.auth.service.TokenStoreService;
import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final TokenStoreService tokenStoreService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Login")
    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    @PostMapping(LOGIN_PATH)
    public String login(@Valid @NotNull @RequestBody LoginParam loginParam) {
        return tokenStoreService.create(loginParam);
    }

    @Operation(summary = "Logout")
    @ApiResponse(responseCode = "200", description = "Ok")
    @PostMapping(LOGOUT_PATH)
    public void logout(@NotNull @RequestHeader("Authorization") String token) {
        tokenStoreService.remove(token);
    }

    @Operation(summary = "Sign up")
    @ApiResponse(responseCode = "200", description = "Ok", content = @Content())
    @PostMapping(SIGN_UP_PATH)
    public void createUser(@Valid @NotNull @RequestBody BaseUserParam baseUserParam) {
        baseUserParam.setPassword(passwordEncoder.encode(baseUserParam.getPassword()));
        userService.create(baseUserParam);
    }
}
