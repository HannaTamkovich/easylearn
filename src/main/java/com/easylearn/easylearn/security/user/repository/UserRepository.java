package com.easylearn.easylearn.security.user.repository;


import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndDeletedFalse(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Collection<UserEntity> findByDateOfLastVisitLessThan(Instant date);

    Collection<UserEntity> findByUsernameNotAndLanguageOrderByUsername(String username, Language language);

    Collection<UserEntity> findByUsernameNotAndLanguageAndUsernameContainsOrderByUsername(String username, Language language, String search);
}
