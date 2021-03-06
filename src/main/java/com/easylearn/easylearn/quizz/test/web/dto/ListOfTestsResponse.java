package com.easylearn.easylearn.quizz.test.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListOfTestsResponse {

    private Long id;
    private String name;
    private Long numberOfQuestions;
    private boolean publicTest;
    private Long createdAt;
    private Float rating;
    private Long numberOfTestPasses;
    private String author;
}
