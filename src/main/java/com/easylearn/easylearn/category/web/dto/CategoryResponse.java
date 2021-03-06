package com.easylearn.easylearn.category.web.dto;

import com.easylearn.easylearn.word.web.dto.DefaultWordResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse extends DefaultCategoryResponse {

    private Collection<DefaultWordResponse> words;
}
