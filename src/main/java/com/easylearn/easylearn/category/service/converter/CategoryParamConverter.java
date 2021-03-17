package com.easylearn.easylearn.category.service.converter;

import com.easylearn.easylearn.category.dto.CategoryParam;
import com.easylearn.easylearn.category.model.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class CategoryParamConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Category toModel(@NotNull CategoryParam categoryParam) {
        return modelMapper.map(categoryParam, Category.class);
    }
}
