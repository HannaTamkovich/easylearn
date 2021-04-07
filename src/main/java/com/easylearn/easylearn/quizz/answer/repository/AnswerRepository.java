package com.easylearn.easylearn.quizz.answer.repository;

import com.easylearn.easylearn.quizz.answer.repository.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long>, JpaSpecificationExecutor<AnswerEntity> {
}
