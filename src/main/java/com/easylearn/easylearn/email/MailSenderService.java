package com.easylearn.easylearn.email;

import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface MailSenderService {

    void sendVerificationMessage(@NotNull @Valid User user);

    void sendNotification(@NotNull @Valid UserEntity userEntity);
}
