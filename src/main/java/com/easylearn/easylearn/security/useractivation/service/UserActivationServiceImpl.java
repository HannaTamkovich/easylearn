package com.easylearn.easylearn.security.useractivation.service;

import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.UserRepository;
import com.easylearn.easylearn.security.useractivation.repository.UserActivationRepository;
import com.easylearn.easylearn.security.useractivation.repository.entity.UserActivationEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class UserActivationServiceImpl implements UserActivationService {

    private final UserRepository userRepository;
    private final UserActivationRepository userActivationRepository;

    @Override
    @Transactional
    public void generateCode(@NotNull @Valid User user) {
        log.info("Generate activation code for user: {}", user.getUsername());

        var activationCode = user.getUsername() + UUID.randomUUID().toString();
        var entity = UserActivationEntity.builder()
                .user(userRepository.getOne(user.getId()))
                .activationCode(activationCode)
                .invalidateDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .build();

        userActivationRepository.save(entity);

        log.debug("Activation code has been generated");
    }

    @Override
    @Transactional(noRollbackFor = EntityNotFoundException.class)
    public void activate(@NotBlank String username) {
        log.info("Activate account");
        userActivationRepository.findByUser_Username(username)
                .ifPresentOrElse(user -> {
                            if (user.getInvalidateDate().isAfter(Instant.now())) {
                                userActivationRepository.delete(user);
                                userRepository.delete(user.getUser());
                                throw new EntityNotFoundException("Ссылка не найдена");
                            }
                            userActivationRepository.delete(user);
                        },
                        () -> {
                            throw new UsernameNotFoundException("Аккаунт не найден. Или был верифицирован ранее.");
                        });
    }
}
