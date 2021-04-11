package com.easylearn.easylearn.quizz.test.service.converter;

import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.model.Test;
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

    public void toUpdateModel(@NotNull Test test, @NotNull TestParam testParam) {
        modelMapper.map(testParam, test);
    }
}
