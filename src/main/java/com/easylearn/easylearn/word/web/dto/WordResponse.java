package com.easylearn.easylearn.word.web.dto;

import com.easylearn.easylearn.category.web.dto.DefaultCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordResponse extends DefaultWordResponse {

    private DefaultCategoryResponse category;
    private Long numberOfAnswers;
    private Long numberOfCorrectAnswers;
}
