package com.easylearn.easylearn.quizz.test.repository;

import com.easylearn.easylearn.quizz.test.repository.entity.TestToUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestToUserRepository extends JpaRepository<TestToUserEntity, Long> {

    Optional<TestToUserEntity> findByUser_UsernameAndTest_Id(String username, Long testId);

    boolean existsByUser_UsernameAndTest_Id(String username, Long testId);

    Long countByTest_Id(Long testId);
}
