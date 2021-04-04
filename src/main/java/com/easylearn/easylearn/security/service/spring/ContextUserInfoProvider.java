package com.easylearn.easylearn.security.service.spring;

import com.easylearn.easylearn.security.service.UserInfoProvider;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ContextUserInfoProvider implements UserInfoProvider {

    @Override
    public User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
