package com.easylearn.easylearn.word.web.dto;

import com.easylearn.easylearn.category.model.Category;
import com.easylearn.easylearn.category.web.dto.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WordResponse {

    private Long id;
    private String word;
    private String translation;
    private CategoryResponse category;
}
