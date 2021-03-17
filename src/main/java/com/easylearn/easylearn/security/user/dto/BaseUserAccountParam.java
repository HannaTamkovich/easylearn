package com.easylearn.easylearn.security.user.dto;

import com.easylearn.easylearn.language.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.easylearn.easylearn.security.user.model.UserAccount.MAX_NAME_SIZE;
import static com.easylearn.easylearn.security.user.model.UserAccount.MAX_USERNAME_SIZE;
import static com.easylearn.easylearn.security.user.model.UserAccount.MIN_NAME_SIZE;
import static com.easylearn.easylearn.security.user.model.UserAccount.MIN_USERNAME_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserAccountParam {

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

    private Language language;
}
