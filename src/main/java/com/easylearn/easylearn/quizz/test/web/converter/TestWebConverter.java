package com.easylearn.easylearn.quizz.test.web.converter;

import com.easylearn.easylearn.quizz.question.web.dto.QuestionResultResponse;
import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.quizz.test.model.TestResult;
import com.easylearn.easylearn.quizz.test.web.dto.ListOfTestsResponse;
import com.easylearn.easylearn.quizz.test.web.dto.PassTestResponse;
import com.easylearn.easylearn.quizz.test.web.dto.TestResponse;
import com.easylearn.easylearn.quizz.test.web.dto.TestResultResponse;
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
        var response = modelMapper.map(test, ListOfTestsResponse.class);
        response.setNumberOfQuestions(test.getQuestions().size());
        return response;
    }

    public TestResponse toResponse(Test test) {
        return modelMapper.map(test, TestResponse.class);
    }

    public PassTestResponse toPassResponse(Test test) {
        return modelMapper.map(test, PassTestResponse.class);
    }

    public TestResultResponse toResultResponse(TestResult test) {
        var response = modelMapper.map(test, TestResultResponse.class);
        response.setNumberOfCorrectAnswers(response.getQuestions().stream().filter(QuestionResultResponse::isCorrect).count());
        return response;
    }
}
