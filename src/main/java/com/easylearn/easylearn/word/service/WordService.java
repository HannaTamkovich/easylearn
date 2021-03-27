package com.easylearn.easylearn.word.service;

import com.easylearn.easylearn.core.dto.response.PageResult;
import com.easylearn.easylearn.word.dto.CardFilter;
import com.easylearn.easylearn.word.dto.WordFilter;
import com.easylearn.easylearn.word.dto.WordParam;
import com.easylearn.easylearn.word.model.Card;
import com.easylearn.easylearn.word.model.Word;
import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface WordService {

    @NotNull
    Word findById(@NotNull Long id);

    @NotNull
    Collection<Word> findAll(@NotNull WordFilter wordFilter);

    @NotNull
    Collection<Word> findAllByCategory(@NotNull Long categoryId);

    @NotNull
    PageResult<Card> findAllCards(@NotNull CardFilter wordFilter);

    void create(@NotNull WordParam wordParam);

    void update(@NotNull Long id, @NotNull WordParam wordParam);

    void delete(@NotNull Long id);

    @NotNull
    boolean answer(@NotNull Long id, @NotBlank String selectedValue);

    @NotNull
    Collection<Word> findAllWithEmptyCategory();

    void addToCategory(@NotNull Long id, @NotNull Long categoryId);

    void deleteFromCategory(@NotNull Long id, @NotNull Long categoryId);
}
