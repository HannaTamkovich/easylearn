package com.easylearn.easylearn.quizz.test.web.dto;

import com.easylearn.easylearn.quizz.question.web.dto.PassQuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassTestResponse {
    private Long id;
    private String name;
    private Collection<PassQuestionResponse> questions;
    private boolean canUserPassTest = true;
    private Long numberOfCorrectAnswers;
    private Integer rate;
}
