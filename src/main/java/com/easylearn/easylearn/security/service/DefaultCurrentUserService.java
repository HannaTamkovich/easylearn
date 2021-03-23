package com.easylearn.easylearn.security.service;

import com.easylearn.easylearn.language.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class DefaultCurrentUserService implements CurrentUserService {

    private final UserInfoProvider userProvider;
    private final LoginStatusProvider loginStatusProvider;

    @Override
    public String getUsername() {
        checkUserIsLoggedIn();

        return userProvider.getUser().getUsername();
    }

    @Override
    public boolean isLoggedIn() {
        return loginStatusProvider.isLoggedIn();
    }

    @Override
    public Language getLanguage() {
        checkUserIsLoggedIn();

        return userProvider.getUser().getLanguage();
    }

    private void checkUserIsLoggedIn() {
        if (!isLoggedIn()) {
            throw new IllegalStateException("No user is logged in");
        }
    }
}
