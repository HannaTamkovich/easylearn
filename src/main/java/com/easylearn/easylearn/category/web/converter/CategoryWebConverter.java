package com.easylearn.easylearn.category.web.converter;

import com.easylearn.easylearn.category.model.Category;
import com.easylearn.easylearn.category.web.dto.CategoryResponse;
import com.easylearn.easylearn.category.web.dto.DefaultCategoryResponse;
import com.easylearn.easylearn.word.model.Word;
import com.easylearn.easylearn.word.service.WordService;
import com.easylearn.easylearn.word.web.dto.DefaultWordResponse;
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

    private final WordService wordService;

    @NotNull
    public Collection<DefaultCategoryResponse> toDefaultResponses(@NotNull Collection<Category> categories) {
        return categories.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @NotNull
    public CategoryResponse toResponse(@NotNull Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }

    @NotNull
    public CategoryResponse toUpdateResponse(Category category) {
        var response = toResponse(category);
        response.setWords(getWordResponse(category));
        return response;
    }

    private Collection<DefaultWordResponse> getWordResponse(Category category) {
        var words = wordService.findAllByCategory(category.getId());
        return words.stream().map(this::toWordResponse).collect(Collectors.toList());
    }

    private DefaultWordResponse toWordResponse(Word word) {
        return modelMapper.map(word, DefaultWordResponse.class);
    }
}
