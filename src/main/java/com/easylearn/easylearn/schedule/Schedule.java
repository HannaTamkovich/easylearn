package com.easylearn.easylearn.schedule;

import com.easylearn.easylearn.email.MailSenderService;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class Schedule {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    //    @Scheduled(cron = "0 0 11 * * *") //every day af 11 am
    @Scheduled(cron = "0 */1 * * * ?")
    public void sendMailNotifications() {
        var yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
        var usersForNotifications = userRepository.findByDateOfLastVisitLessThan(yesterday);
        usersForNotifications.forEach(mailSenderService::sendNotification);
    }
}