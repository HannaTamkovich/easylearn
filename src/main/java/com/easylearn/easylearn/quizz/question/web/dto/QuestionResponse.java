package com.easylearn.easylearn.quizz.question.web.dto;

import com.easylearn.easylearn.quizz.answer.web.dto.AnswerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private Long id;
    private String text;
    private Collection<AnswerResponse> answers;
}
