package com.easylearn.easylearn.security.service;


import com.easylearn.easylearn.security.user.model.User;

public interface UserInfoAccountProvider {
    User load();

    User getUserAccount();
}
