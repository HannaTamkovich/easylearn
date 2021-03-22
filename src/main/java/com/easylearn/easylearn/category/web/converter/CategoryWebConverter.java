package com.easylearn.easylearn.category.web.converter;

import com.easylearn.easylearn.category.model.Category;
import com.easylearn.easylearn.category.web.dto.CategoryResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryWebConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Collection<CategoryResponse> toResponses(@NotNull Collection<Category> categories) {
        return categories.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @NotNull
    public CategoryResponse toResponse(@NotNull Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }
}
