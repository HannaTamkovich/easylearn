package com.easylearn.easylearn.quizz.answer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerParam {

    @NotNull
    private String text;

    @NotNull
    private Boolean isCorrectAnswer;
}
