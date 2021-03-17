package com.easylearn.easylearn.security.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserAccountParam {

    @NotNull
    private Long id;

    @NotBlank
    private String password;
}
