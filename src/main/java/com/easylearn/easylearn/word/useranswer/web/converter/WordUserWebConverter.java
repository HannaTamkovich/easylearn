package com.easylearn.easylearn.word.useranswer.web.converter;

import com.easylearn.easylearn.word.useranswer.model.WordUserAnswer;
import com.easylearn.easylearn.word.useranswer.web.dto.WordUserAnswerResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class WordUserWebConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public WordUserAnswerResponse toResponse(@NotNull WordUserAnswer wordUserAnswer) {
        return modelMapper.map(wordUserAnswer, WordUserAnswerResponse.class);
    }
}
