package com.easylearn.easylearn.word.dto;

import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Data
public class WordFilter {

    @Nullable
    private Long categoryId;

    @NotNull
    private String username;
}
