package com.easylearn.easylearn.category.dto;

import com.easylearn.easylearn.language.model.Language;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class CategoryParam {

    @NotNull
    private String name;

    @NotNull
    private Language language;
}
