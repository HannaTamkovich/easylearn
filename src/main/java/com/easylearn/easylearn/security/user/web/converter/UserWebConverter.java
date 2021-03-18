package com.easylearn.easylearn.security.user.web.converter;

import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.web.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Validated
@Component
@AllArgsConstructor
public class UserWebConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Collection<UserResponse> toResponses(@NotNull Collection<User> users) {
        return users.stream().map(this::toResponse).collect(toSet());
    }

    @NotNull
    public UserResponse toResponse(@NotNull User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
