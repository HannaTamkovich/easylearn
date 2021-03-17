package com.easylearn.easylearn.security.user.service.converter;

import com.easylearn.easylearn.security.user.dto.BaseUserAccountParam;
import com.easylearn.easylearn.security.user.model.UserAccount;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class UserAccountParamConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public UserAccount toModel(BaseUserAccountParam baseUserAccountParam) {
        return modelMapper.map(baseUserAccountParam, UserAccount.class);
    }
}
