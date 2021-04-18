package com.easylearn.easylearn.word.useranswer.web.dto;

import lombok.Data;

@Data
public class WordUserAnswerResponse {

    private Long id;
    private Long allAnswers = 0L;
    private Long correctAnswers = 0L;
}
