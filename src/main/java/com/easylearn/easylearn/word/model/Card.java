package com.easylearn.easylearn.word.model;

import com.easylearn.easylearn.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    @NotNull
    private Long id;

    @NotNull
    private String word;

    @Nullable
    private Category category;

    @NotNull
    Collection<String> translations;
}
