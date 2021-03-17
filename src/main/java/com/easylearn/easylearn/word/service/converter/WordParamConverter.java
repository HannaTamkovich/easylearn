package com.easylearn.easylearn.word.service.converter;

import com.easylearn.easylearn.word.dto.WordParam;
import com.easylearn.easylearn.word.model.Word;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class WordParamConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Word toModel(@NotNull WordParam wordParam) {
        return modelMapper.map(wordParam, Word.class);
    }
}
