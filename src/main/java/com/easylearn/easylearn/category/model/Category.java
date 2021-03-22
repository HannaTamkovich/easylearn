package com.easylearn.easylearn.category.model;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.security.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Language language;

    //@NotNull
    private User user;
}
