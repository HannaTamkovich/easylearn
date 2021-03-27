package com.easylearn.easylearn.email;

import com.easylearn.easylearn.security.user.model.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface MailSenderService {

    void sendVerificationMessage(@NotNull @Valid User user);
}
