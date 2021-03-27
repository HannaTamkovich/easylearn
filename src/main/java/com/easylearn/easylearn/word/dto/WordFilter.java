package com.easylearn.easylearn.word.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordFilter {

    @Nullable
    private Long categoryId;

    @NotNull
    private String username;
}
