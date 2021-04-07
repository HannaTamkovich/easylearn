package com.easylearn.easylearn.quizz.question.model;

import com.easylearn.easylearn.quizz.answer.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    private Long id;

    @NotNull
    private String text;

    @NotNull
    private Collection<Answer> answers;
}
