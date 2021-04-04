package com.easylearn.easylearn.security.service;

import com.easylearn.easylearn.language.model.Language;

public interface CurrentUserService {

    String getUsername();

    boolean isLoggedIn();

    Language getLanguage();
}
