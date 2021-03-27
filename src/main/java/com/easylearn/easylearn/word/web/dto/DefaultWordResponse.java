package com.easylearn.easylearn.word.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultWordResponse {

    private Long id;
    private String word;
    private String translation;
}
