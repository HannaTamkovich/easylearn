package com.easylearn.easylearn.schedule;

import com.easylearn.easylearn.email.MailSenderService;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
@EnableScheduling
public class Schedule {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    //@Scheduled(cron = "0 */1 * * * ?") //every minute
    @Scheduled(cron = "0 11 * * * ?") //every day at 11 am
    public void sendMailNotifications() {
        log.info("Start to send notifications");
        var yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
        var usersForNotifications = userRepository.findByDateOfLastVisitLessThan(yesterday);
        usersForNotifications.forEach(mailSenderService::sendNotification);
        log.info("Notifications have been sent");
    }
}