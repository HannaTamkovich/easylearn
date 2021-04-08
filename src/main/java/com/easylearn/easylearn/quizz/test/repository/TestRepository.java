package com.easylearn.easylearn.quizz.test.repository;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.quizz.test.repository.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TestRepository extends JpaRepository<TestEntity, Long> {

    Collection<TestEntity> findByUser_UsernameAndNameContainsOrderByName(String username, String name);

    Collection<TestEntity> findByLanguageAndNameContainsAndUser_UsernameNotAndIsPublicTestTrueOrderByName(Language language, String username, String name);
}
