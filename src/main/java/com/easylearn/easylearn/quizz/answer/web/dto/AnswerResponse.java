package com.easylearn.easylearn.quizz.answer.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {

    private Long id;
    private String text;
    private boolean isCorrectAnswer;
}
