package com.easylearn.easylearn.config;

import com.easylearn.easylearn.security.user.model.User;
import com.easylearn.easylearn.security.user.repository.converter.UserEntityConverter;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@AllArgsConstructor
public class AuditConfig {

    private final UserEntityConverter userEntityConverter;

    @Bean
    public AuditorAware<UserEntity> auditorAware() {
        return () -> {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.of(userEntityConverter.toEntity(user));
        };
    }

}
