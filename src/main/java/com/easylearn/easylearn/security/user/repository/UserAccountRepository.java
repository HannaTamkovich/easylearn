package com.easylearn.easylearn.security.user.repository;


import com.easylearn.easylearn.security.user.repository.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByUsername(String username);

    Optional<UserAccountEntity> findByUsernameAndDeletedFalse(String username);

    boolean existsByUsername(String username);
}
