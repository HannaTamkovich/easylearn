package com.easylearn.easylearn.category.web.dto;

import com.easylearn.easylearn.language.web.dto.LanguageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private LanguageResponse language;
}
