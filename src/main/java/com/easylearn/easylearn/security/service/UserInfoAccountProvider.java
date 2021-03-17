package com.easylearn.easylearn.security.service;


import com.easylearn.easylearn.security.user.model.UserAccount;

public interface UserInfoAccountProvider {
    UserAccount load();

    UserAccount getUserAccount();
}
