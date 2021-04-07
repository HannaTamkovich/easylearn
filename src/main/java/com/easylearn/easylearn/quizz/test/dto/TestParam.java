package com.easylearn.easylearn.quizz.test.dto;

import com.easylearn.easylearn.quizz.question.dto.QuestionParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class TestParam {

    @NotNull
    private String name;

    @NotNull
    private Collection<QuestionParam> questions;

    @NotNull
    private Boolean isPublicTest;
}
