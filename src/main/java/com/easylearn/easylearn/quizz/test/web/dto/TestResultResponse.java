package com.easylearn.easylearn.quizz.test.web.dto;

import com.easylearn.easylearn.quizz.question.web.dto.QuestionResultResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultResponse {

    private Long id;
    private Collection<QuestionResultResponse> questions;
    private Long numberOfCorrectAnswers;
}
