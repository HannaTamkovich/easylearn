package com.easylearn.easylearn.quizz.test.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListOfTestsResponse {

    private Long id;
    private String name;
    private Integer numberOfQuestions;
    private boolean publicTest;
    private Long createdAt;
}
