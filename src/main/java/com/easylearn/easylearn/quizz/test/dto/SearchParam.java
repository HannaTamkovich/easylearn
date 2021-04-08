package com.easylearn.easylearn.quizz.test.dto;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParam {

    @NotNull
    private boolean onlyUserTests = true;

    @Nullable
    private String search;
}
