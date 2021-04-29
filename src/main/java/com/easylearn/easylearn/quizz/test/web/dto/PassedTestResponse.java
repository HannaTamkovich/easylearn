package com.easylearn.easylearn.quizz.test.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PassedTestResponse {

    private Long id;
    private String name;
    private Long numberOfQuestions;
    private Long numberOfCorrectAnswers;
}
