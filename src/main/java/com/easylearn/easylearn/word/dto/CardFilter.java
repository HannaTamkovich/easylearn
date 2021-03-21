package com.easylearn.easylearn.word.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class CardFilter {

    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    @PositiveOrZero
    private int pageNumber = DEFAULT_PAGE_NUMBER;

    @Positive
    private int pageSize = DEFAULT_PAGE_SIZE;

    @NotNull
    private boolean onlyUserWords = true;
}
