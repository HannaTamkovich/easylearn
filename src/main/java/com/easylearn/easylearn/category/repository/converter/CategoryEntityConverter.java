package com.easylearn.easylearn.category.repository.converter;

import com.easylearn.easylearn.category.model.Category;
import com.easylearn.easylearn.category.repository.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Category toModel(@NotNull CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, Category.class);
    }

    @NotNull
    public Collection<Category> toModels(@NotNull Collection<CategoryEntity> categoryEntities) {
        return categoryEntities.stream().map(this::toModel).collect(Collectors.toSet());
    }

    @NotNull
    public CategoryEntity toEntity(@NotNull Category category) {
        return modelMapper.map(category, CategoryEntity.class);
    }

    @NotNull
    public Collection<CategoryEntity> toEntities(@NotNull Collection<Category> categories) {
        return categories.stream().map(this::toEntity).collect(Collectors.toSet());
    }

}
