package com.easylearn.easylearn.category.service;

import com.easylearn.easylearn.category.dto.CategoryParam;
import com.easylearn.easylearn.category.model.Category;
import com.sun.istack.NotNull;

import java.util.Collection;

public interface CategoryService {

    @NotNull
    Category findById(@NotNull Long id);

    @NotNull
    Collection<Category> findAllForCurrentUser();

    void create(@NotNull CategoryParam categoryParam);

    void update(@NotNull Long id, @NotNull CategoryParam categoryParam);

    void delete(@NotNull Long id);
}
