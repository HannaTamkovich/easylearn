package com.easylearn.easylearn.word.web.converter;

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

    @NotNull
    public Collection<WordResponse> toResponses(@NotNull Collection<Word> words) {
        return words.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    @NotNull
    public WordResponse toResponse(@NotNull Word word) {
        return modelMapper.map(word, WordResponse.class);
    }
}
