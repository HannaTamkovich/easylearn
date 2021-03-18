package com.easylearn.easylearn.security.service.spring;

import com.easylearn.easylearn.security.service.UserInfoAccountProvider;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ContextUserInfoProvider implements UserInfoAccountProvider {

    private final UserService userService;

    @Override
    public User load() {
        return userService.loadByUsername(getUserAccount().getUsername());
    }

    @Override
    public User getUserAccount() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
