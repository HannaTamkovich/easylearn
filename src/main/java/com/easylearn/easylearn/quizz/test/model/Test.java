package com.easylearn.easylearn.quizz.test.model;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.quizz.question.model.Question;
import com.easylearn.easylearn.security.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Test {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private User user;

    @NotNull
    private Language language;

    @NotNull
    private Collection<Question> questions;

    @NotNull
    private Boolean isPublicTest;
}
