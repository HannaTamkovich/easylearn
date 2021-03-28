package com.easylearn.easylearn.job;

import com.easylearn.easylearn.email.MailSenderService;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@AllArgsConstructor
public class Task implements Tasklet {

    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("Start email task");

        var yesterday = Instant.now().minus(1, ChronoUnit.DAYS);
        var usersForNotifications = userRepository.findByDateOfLastVisitLessThan(yesterday);
        usersForNotifications.forEach(mailSenderService::sendNotification);

        log.info("Finish email task");
        return RepeatStatus.FINISHED;
    }
}