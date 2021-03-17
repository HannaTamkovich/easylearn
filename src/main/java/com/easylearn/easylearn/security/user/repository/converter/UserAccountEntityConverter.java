package com.easylearn.easylearn.security.user.repository.converter;

import com.easylearn.easylearn.security.user.model.UserAccount;
import com.easylearn.easylearn.security.user.repository.entity.UserAccountEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static java.util.stream.Collectors.toSet;

@Component
@AllArgsConstructor
public class UserAccountEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public UserAccount toModel(@NotNull UserAccountEntity userAccountEntity) {
        return modelMapper.map(userAccountEntity, UserAccount.class);
    }

    @NotNull
    public Collection<UserAccount> toModels(@NotNull Collection<UserAccountEntity> userEntities) {
        return userEntities.stream().map(this::toModel).collect(toSet());
    }

    @NotNull
    public UserAccountEntity toEntity(@NotNull UserAccount userAccount) {
        return modelMapper.map(userAccount, UserAccountEntity.class);
    }

    @NotNull
    public Collection<UserAccountEntity> toEntities(@NotNull Collection<UserAccount> userAccounts) {
        return userAccounts.stream().map(this::toEntity).collect(toSet());
    }
}
