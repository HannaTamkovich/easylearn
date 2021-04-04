package com.easylearn.easylearn.security.user.repository.converter;

import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public User toModel(@NotNull UserEntity userEntity) {
        return modelMapper.map(userEntity, User.class);
    }

    @NotNull
    public Collection<User> toModels(@NotNull Collection<UserEntity> userEntities) {
        return userEntities.stream().map(this::toModel).collect(Collectors.toList());
    }

    @NotNull
    public UserEntity toEntity(@NotNull User user) {
        return modelMapper.map(user, UserEntity.class);
    }

    @NotNull
    public Collection<UserEntity> toEntities(@NotNull Collection<User> users) {
        return users.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
