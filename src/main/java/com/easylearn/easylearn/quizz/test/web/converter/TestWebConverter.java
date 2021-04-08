package com.easylearn.easylearn.quizz.test.web.converter;

import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.quizz.test.web.dto.ListOfTestsResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TestWebConverter {

    private final ModelMapper modelMapper;

    public Collection<ListOfTestsResponse> toListResponses(Collection<Test> tests) {
        return tests.stream().map(this::toListResponse).collect(Collectors.toList());
    }

    public ListOfTestsResponse toListResponse(Test test) {
        return new ListOfTestsResponse(test.getId(), test.getName(), test.getQuestions().size(), test.getIsPublicTest());
    }
}
