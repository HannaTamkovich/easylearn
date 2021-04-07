package com.easylearn.easylearn.quizz.question.dto;

import com.easylearn.easylearn.quizz.answer.dto.AnswerParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class QuestionParam {

    @NotNull
    private String text;

    @NotNull
    private Collection<AnswerParam> answers;
}
