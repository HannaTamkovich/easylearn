package com.easylearn.easylearn.security.user.web.converter;

import com.easylearn.easylearn.security.user.model.UserAccount;
import com.easylearn.easylearn.security.user.web.dto.UserAccountResponse;
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
public class UserAccountWebConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Collection<UserAccountResponse> toResponses(@NotNull Collection<UserAccount> userAccounts) {
        return userAccounts.stream().map(this::toResponse).collect(toSet());
    }

    @NotNull
    public UserAccountResponse toResponse(@NotNull UserAccount userAccount) {
        return modelMapper.map(userAccount, UserAccountResponse.class);
    }
}
