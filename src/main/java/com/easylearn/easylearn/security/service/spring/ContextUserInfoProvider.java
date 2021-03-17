package com.easylearn.easylearn.security.service.spring;

import com.easylearn.easylearn.security.service.UserInfoAccountProvider;
import com.easylearn.easylearn.security.user.model.UserAccount;
import com.easylearn.easylearn.security.user.service.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ContextUserInfoProvider implements UserInfoAccountProvider {

    private final UserAccountService userAccountService;

    @Override
    public UserAccount load() {
        return userAccountService.loadByUsername(getUserAccount().getUsername());
    }

    @Override
    public UserAccount getUserAccount() {
        return (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
