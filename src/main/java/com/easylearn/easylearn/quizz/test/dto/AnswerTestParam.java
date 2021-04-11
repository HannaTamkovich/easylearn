package com.easylearn.easylearn.quizz.test.dto;

import com.easylearn.easylearn.quizz.question.dto.AnswerQuestionParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class AnswerTestParam {

    @NotNull
    private Long id;

    @NotNull
    private Collection<AnswerQuestionParam> questions;
}
