package com.easylearn.easylearn.security.user.service.converter;

import com.easylearn.easylearn.security.user.dto.BaseUserParam;
import com.easylearn.easylearn.security.user.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class UserParamConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public User toModel(BaseUserParam baseUserParam) {
        return modelMapper.map(baseUserParam, User.class);
    }
}
