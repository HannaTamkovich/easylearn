package com.easylearn.easylearn.security.useractivation.repository;

import com.easylearn.easylearn.security.useractivation.repository.entity.UserActivationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserActivationRepository extends JpaRepository<UserActivationEntity, Long> {

    Optional<UserActivationEntity> findByUser_Username(String username);

    boolean existsByUser_Username(String username);
}
