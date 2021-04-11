package com.easylearn.easylearn.quizz.question.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class AnswerQuestionParam {

    @NotNull
    private Long id;

    @NotNull
    private Collection<Long> answers;
}
