package com.easylearn.easylearn.security.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.easylearn.easylearn.security.user.model.User.MAX_NAME_SIZE;
import static com.easylearn.easylearn.security.user.model.User.MAX_USERNAME_SIZE;
import static com.easylearn.easylearn.security.user.model.User.MIN_NAME_SIZE;
import static com.easylearn.easylearn.security.user.model.User.MIN_USERNAME_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserParam {

    @Size(min = MIN_USERNAME_SIZE, max = MAX_USERNAME_SIZE)
    private String username;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @NotNull
    private String firstName;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @NotNull
    private String secondName;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @NotNull
    private String middleName;

    @Email
    @NotNull
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private String language;
}
