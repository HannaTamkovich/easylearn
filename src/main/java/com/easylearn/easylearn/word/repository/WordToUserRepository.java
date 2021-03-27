package com.easylearn.easylearn.word.repository;

import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordToUserRepository extends JpaRepository<WordToUserEntity, Long> {

    boolean existsByCategory_Id(Long categoryId);

    boolean existsByUserIdAndWordId(Long userId, Long wordId);

    void deleteByUserIdAndWordId(Long userId, Long wordId);

    long countByWordId(Long wordId);

    void deleteByWordId(Long wordId);

    Optional<WordToUserEntity> findEntityByWordIdAndUserId(Long wordId, Long userId);

    Optional<WordToUserEntity> findEntityByWordIdAndUserIdAndCategory_Id(Long wordId, Long userId, Long categoryId);

    WordToUserEntity findByWordIdAndUserId(Long wordId, Long userId);

    List<WordToUserEntity> findAllByUserIdOrderByDateOfLastAnswerAsc(Long userId);
}
