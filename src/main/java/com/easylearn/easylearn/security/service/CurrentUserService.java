package com.easylearn.easylearn.security.service;

import com.easylearn.easylearn.language.model.Language;

public interface CurrentUserService {

    /**
     * Returns the current user's name.
     *
     * @return .
     * @throws IllegalStateException if no user is logged in.
     */
    String getUsername();

    boolean isLoggedIn();

    Language getLanguage();
}
