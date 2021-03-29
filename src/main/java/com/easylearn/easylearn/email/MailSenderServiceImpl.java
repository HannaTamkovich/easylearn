package com.easylearn.easylearn.email;

import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class MailSenderServiceImpl implements MailSenderService {

    private final MailSender mailSender;

    @Override
    @Transactional
    public void sendVerificationMessage(@NotNull @Valid User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "Необходимо верифицировать аккаунт по следующей ссылке: http://localhost:8080/api/activate/%s",
                    user.getUsername(), user.getUsername()
            );


            mailSender.send(user.getEmail(), "Активация аккаунта", message);
        }
    }

    @Override
    public void sendNotification(@NotNull UserEntity userEntity) {
        //TODO
        System.out.println("Email sent");
    }
}
