package com.easylearn.easylearn.security.service.spring;

import com.easylearn.easylearn.security.service.LoginStatusProvider;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ContextLoginStatusProvider implements LoginStatusProvider {

    @Override
    public boolean isLoggedIn() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext != null && securityContext.getAuthentication() != null;
    }
}
