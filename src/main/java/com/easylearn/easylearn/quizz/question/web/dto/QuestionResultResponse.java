package com.easylearn.easylearn.quizz.question.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultResponse {

    private Long id;
    private boolean correct;
}
