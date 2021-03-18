package com.easylearn.easylearn.word.dto;

import lombok.Data;

import javax.annotation.Nullable;

@Data
public class WordFilter {

    @Nullable
    private Long categoryId;

    @Nullable
    private Long userId;
}
