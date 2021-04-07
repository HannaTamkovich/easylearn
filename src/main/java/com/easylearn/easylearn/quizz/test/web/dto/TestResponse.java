package com.easylearn.easylearn.quizz.test.web.dto;

import com.easylearn.easylearn.quizz.question.web.dto.QuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse {

    private Long id;
    private String name;
    private Collection<QuestionResponse> questions;
}
