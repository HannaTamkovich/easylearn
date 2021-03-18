package com.easylearn.easylearn.security.auth.service;

import com.easylearn.easylearn.security.auth.dto.LoginParam;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.service.UserService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Validated
@Service
@AllArgsConstructor
public class TokenStoreServiceImpl implements TokenStoreService {

    private static final int TOKEN_EXPIRATION_IN_MINUTES = 60;

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Cache<String, String> tokenUsernameMap = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofMinutes(TOKEN_EXPIRATION_IN_MINUTES)).build();

    @Transactional(readOnly = true)
    @Override
    @NotNull
    public String create(@Valid @NotNull LoginParam loginParam) {
        var userAccountOpt = userService.findByUsernameAndActiveStatus(loginParam.getUsername());
        validateUsernameAndPassword(userAccountOpt, loginParam.getPassword());
        var token = "Bearer " + UUID.randomUUID().toString();
        tokenUsernameMap.put(token, userAccountOpt.orElseThrow().getUsername());
        return token;
    }

    private void validateUsernameAndPassword(Optional<User> userAccount, String password) {
        userAccount
                .ifPresentOrElse(account -> {
                    if (!passwordEncoder.matches(password, account.getPassword())) {
                        throw new BadCredentialsException("Неверный логин и/или пароль.");
                    }
                }, () -> {
                    throw new UsernameNotFoundException("Неверный логин и/или пароль.");
                });
    }

    @Override
    public void remove(@NotNull String token) {
        tokenUsernameMap.invalidate(token);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByToken(@NotNull String token) {
        var usernameOpt = Optional.ofNullable(tokenUsernameMap.getIfPresent(token));
        return usernameOpt.map(userService::loadByUsername);
    }
}
