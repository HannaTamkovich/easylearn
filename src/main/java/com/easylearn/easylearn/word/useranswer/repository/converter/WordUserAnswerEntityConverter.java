package com.easylearn.easylearn.word.useranswer.repository.converter;

import com.easylearn.easylearn.word.useranswer.model.WordUserAnswer;
import com.easylearn.easylearn.word.useranswer.repository.entity.WordUserAnswerEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class WordUserAnswerEntityConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public WordUserAnswer toModel(@NotNull WordUserAnswerEntity wordUserAnswer) {
        return modelMapper.map(wordUserAnswer, WordUserAnswer.class);
    }
}
