package com.easylearn.easylearn.word.useranswer.repository;

import com.easylearn.easylearn.word.useranswer.repository.entity.WordUserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordUserAnswerRepository extends JpaRepository<WordUserAnswerEntity, Long> {

    Optional<WordUserAnswerEntity> findByUser_Username(String username);
}
