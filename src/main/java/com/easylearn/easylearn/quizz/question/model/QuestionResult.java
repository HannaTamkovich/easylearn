package com.easylearn.easylearn.quizz.question.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResult {

    @NotNull
    private Long id;

    @NotNull
    private boolean correct;
}
