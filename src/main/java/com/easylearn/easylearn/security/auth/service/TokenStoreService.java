package com.easylearn.easylearn.security.auth.service;

import com.easylearn.easylearn.security.auth.dto.LoginParam;
import com.easylearn.easylearn.security.user.model.User;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface TokenStoreService {

    @NotNull
    String create(@Valid @NotNull @RequestBody LoginParam loginParam);

    void remove(@NotNull String token);

    Optional<User> findByToken(@NotNull String token);
}
