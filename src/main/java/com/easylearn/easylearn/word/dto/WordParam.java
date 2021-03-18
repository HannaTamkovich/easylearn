package com.easylearn.easylearn.word.dto;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Data
public class WordParam {

    @NotNull
    private String word;

    @NotNull
    private String translation;

    @Nullable
    private Long categoryId;
}
