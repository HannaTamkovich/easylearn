package com.easylearn.easylearn.quizz.test.testrating.repository;

import com.easylearn.easylearn.quizz.test.testrating.repository.entity.TestRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TestRateRepository extends JpaRepository<TestRateEntity, Long> {

    boolean existsByUser_IdAndTest_Id(Long userId, Long testId);

    Optional<TestRateEntity> findByUser_UsernameAndTest_Id(String username, Long testId);

    @Query(value = "SELECT AVG(t.rate) FROM TEST_RATING t WHERE t.TEST_ID = ?1", nativeQuery = true)
    Float averageRateByTestId(Long testId);
}
