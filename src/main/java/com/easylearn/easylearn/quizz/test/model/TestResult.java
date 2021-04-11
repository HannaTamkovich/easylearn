package com.easylearn.easylearn.quizz.test.model;

import com.easylearn.easylearn.quizz.question.model.QuestionResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {

    @NotNull
    private Long id;

    @NotNull
    private Collection<QuestionResult> questions;
}
