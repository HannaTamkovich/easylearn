package com.easylearn.easylearn.word.model;

import com.easylearn.easylearn.language.model.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Word {

    @NotNull
    private Long id;

    @NotNull
    private String word;

    @NotNull
    private String translation;

    @NotNull
    private Language language;
}
