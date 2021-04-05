package com.easylearn.easylearn.word.useranswer.model;

import com.easylearn.easylearn.security.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordUserAnswer {

    private Long id;

    @NotNull
    private UserEntity user;

    @NotNull
    private Long allAnswers = 0L;

    @NotNull
    private Long correctAnswers = 0L;
}
