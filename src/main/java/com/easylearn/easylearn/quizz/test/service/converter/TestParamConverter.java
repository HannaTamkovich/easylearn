package com.easylearn.easylearn.quizz.test.service.converter;

import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.word.dto.WordParam;
import com.easylearn.easylearn.word.model.Word;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class TestParamConverter {

    private final ModelMapper modelMapper;

    @NotNull
    public Test toModel(@NotNull TestParam testParam) {
        return modelMapper.map(testParam, Test.class);
    }
}