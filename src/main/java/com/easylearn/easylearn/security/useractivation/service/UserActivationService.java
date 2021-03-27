package com.easylearn.easylearn.security.useractivation.service;

import com.easylearn.easylearn.security.user.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface UserActivationService {

    void generateCode(@NotNull @Valid User user);

    void activate(@NotBlank String username);
}
