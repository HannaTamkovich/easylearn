package com.easylearn.easylearn.word.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse {

    private Long id;
    private String selectedValue;
    private boolean correctAnswer;
}
