package com.easylearn.easylearn.category.web.controller;

import com.easylearn.easylearn.category.dto.CategoryParam;
import com.easylearn.easylearn.category.service.CategoryService;
import com.easylearn.easylearn.category.web.converter.CategoryWebConverter;
import com.easylearn.easylearn.category.web.dto.CategoryResponse;
import com.easylearn.easylearn.category.web.dto.DefaultCategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(CategoryController.CATEGORY_PATH)
@CrossOrigin
public class CategoryController {

    public static final String CATEGORY_PATH = "/categories";
    public static final String PATH_BY_ID = "/{id}";
    public static final String PATH_TO_UPDATE = "/{id}/details";

    private final CategoryService categoryService;
    private final CategoryWebConverter categoryWebConverter;

    @GetMapping(PATH_BY_ID)
    public DefaultCategoryResponse findById(@NotNull @PathVariable("id") Long id) {
        var category = categoryService.findById(id);
        return categoryWebConverter.toResponse(category);
    }

    @GetMapping
    public Collection<DefaultCategoryResponse> findAllForCurrentUser() {
        var categories = categoryService.findAllForCurrentUser();
        return categoryWebConverter.toDefaultResponses(categories);
    }

    @GetMapping(PATH_TO_UPDATE)
    public CategoryResponse findToUpdate(@NotNull @PathVariable("id") Long id) {
        var category = categoryService.findById(id);
        return categoryWebConverter.toUpdateResponse(category);
    }

    @PostMapping
    public void create(@Valid @NotNull @RequestBody CategoryParam categoryParam) {
        categoryService.create(categoryParam);
    }

    @PutMapping(PATH_BY_ID)
    public void update(@NotNull @PathVariable("id") Long id, @Valid @NotNull @RequestBody CategoryParam categoryParam) {
        categoryService.update(id, categoryParam);
    }

    @DeleteMapping(PATH_BY_ID)
    public void delete(@NotNull @PathVariable("id") Long id) {
        categoryService.delete(id);
    }
}
