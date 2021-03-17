package com.easylearn.easylearn.config;

import com.easylearn.easylearn.security.user.model.UserAccount;
import com.easylearn.easylearn.security.user.repository.converter.UserAccountEntityConverter;
import com.easylearn.easylearn.security.user.repository.entity.UserAccountEntity;
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

    private final UserAccountEntityConverter userAccountEntityConverter;

    @Bean
    public AuditorAware<UserAccountEntity> auditorAware() {
        return () -> {
            var userAccount = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return Optional.of(userAccountEntityConverter.toEntity(userAccount));
        };
    }

}
