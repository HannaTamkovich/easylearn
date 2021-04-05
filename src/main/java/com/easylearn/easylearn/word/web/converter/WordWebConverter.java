package com.easylearn.easylearn.word.web.converter;

import com.easylearn.easylearn.category.service.CategoryService;
import com.easylearn.easylearn.category.web.dto.DefaultCategoryResponse;
import com.easylearn.easylearn.word.model.Word;
import com.easylearn.easylearn.word.web.dto.WordResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WordWebConverter {

    private final ModelMapper modelMapper;

    private final CategoryService categoryService;

    @NotNull
    public Collection<WordResponse> toResponses(@NotNull Collection<Word> words, @NotNull String username) {
        return words.stream().map(this::toResponse)
                .peek(it -> it.setCategory(getUserCategoryResponse(it.getId(), username)))
                .collect(Collectors.toList());
    }

    @NotNull
    public WordResponse toResponse(@NotNull Word word) {
        var wordResponse = modelMapper.map(word, WordResponse.class);
        wordResponse.setCategory(getCategoryResponse(word.getId()));
        return wordResponse;
    }

    @NotNull
    public Collection<WordResponse> toBaseResponses(@NotNull Collection<Word> words) {
        return words.stream().map(this::toBaseResponse).collect(Collectors.toList());
    }

    @NotNull
    public WordResponse toBaseResponse(@NotNull Word word) {
        return modelMapper.map(word, WordResponse.class);
    }

    private DefaultCategoryResponse getCategoryResponse(Long wordId) {
        return categoryService.findByWordIdForCurrentUser(wordId)
                .map(it -> modelMapper.map(it, DefaultCategoryResponse.class))
                .orElse(null);
    }

    private DefaultCategoryResponse getUserCategoryResponse(Long wordId, String username) {
        return categoryService.findByWordIdForUser(wordId, username)
                .map(it -> modelMapper.map(it, DefaultCategoryResponse.class))
                .orElse(null);
    }
}
