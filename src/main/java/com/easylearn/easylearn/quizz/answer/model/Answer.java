package com.easylearn.easylearn.quizz.answer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {

    private Long id;

    @NotNull
    private String text;

    @NotNull
    private boolean isCorrectAnswer;
}
