package com.easylearn.easylearn.word.dto;

import com.easylearn.easylearn.language.model.Language;
import lombok.Data;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class WordFilter {

    @Nullable
    private Collection<Long> categoryIds;

    @Nullable
    private Collection<Language> languages;

    @Nullable
    private Long userId;
}
