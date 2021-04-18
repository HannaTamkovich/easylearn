package com.easylearn.easylearn.word.repository;

import com.easylearn.easylearn.word.repository.entity.WordToUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WordToUserRepository extends JpaRepository<WordToUserEntity, Long> {

    boolean existsByCategory_Id(Long categoryId);

    boolean existsByUser_UsernameAndWord_Id(String username, Long wordId);

    void deleteByUser_UsernameAndWord_Id(String username, Long wordId);

    Long countByWord_Id(Long wordId);

    void deleteByWord_Id(Long wordId);

    Optional<WordToUserEntity> findEntityByWord_IdAndUser_Username(Long wordId, String username);

    Optional<WordToUserEntity> findEntityByWord_IdAndUser_UsernameAndCategory_Id(Long wordId, String username, Long categoryId);

    WordToUserEntity findByWord_IdAndUser_Username(Long wordId, String username);

    List<WordToUserEntity> findAllByUser_UsernameOrderByDateOfLastAnswerAsc(String username);

    Long countByUser_Id(Long userId);
}
