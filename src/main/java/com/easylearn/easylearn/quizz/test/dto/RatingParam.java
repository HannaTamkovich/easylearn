package com.easylearn.easylearn.quizz.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingParam {

    @NotNull
    private Integer rating;
}
