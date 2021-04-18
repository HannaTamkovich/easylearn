package com.easylearn.easylearn.quizz.test.web.converter;

import com.easylearn.easylearn.quizz.question.web.dto.QuestionResultResponse;
import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.quizz.test.model.TestResult;
import com.easylearn.easylearn.quizz.test.repository.TestToUserRepository;
import com.easylearn.easylearn.quizz.test.testrating.repository.TestRateRepository;
import com.easylearn.easylearn.quizz.test.web.dto.ListOfTestsResponse;
import com.easylearn.easylearn.quizz.test.web.dto.PassTestResponse;
import com.easylearn.easylearn.quizz.test.web.dto.TestResponse;
import com.easylearn.easylearn.quizz.test.web.dto.TestResultResponse;
import com.easylearn.easylearn.security.service.CurrentUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TestWebConverter {

    private final ModelMapper modelMapper;
    public final CurrentUserService currentUserService;
    private final TestToUserRepository testToUserRepository;
    private final TestRateRepository testRateRepository;

    public Collection<ListOfTestsResponse> toListResponses(Collection<Test> tests) {
        return tests.stream().map(this::toListResponse).collect(Collectors.toList());
    }

    public ListOfTestsResponse toListResponse(Test test) {
        var response = modelMapper.map(test, ListOfTestsResponse.class);
        response.setNumberOfQuestions(test.getQuestions().size());
        var rating = testRateRepository.averageRateByTestId(test.getId());
        response.setRating(Objects.nonNull(rating) ? rating : 0);
        response.setNumberOfTestPasses(testToUserRepository.countByTest_Id(test.getId()));
        return response;
    }

    public TestResponse toResponse(Test test) {
        return modelMapper.map(test, TestResponse.class);
    }

    public PassTestResponse toPassResponse(Test test) {
        var response = modelMapper.map(test, PassTestResponse.class);

        var username = currentUserService.getUsername();
        testToUserRepository.findByUser_UsernameAndTest_Id(username, test.getId())
                .ifPresent(it -> {
                    response.setNumberOfCorrectAnswers(it.getNumberOfCorrectAnswer());
                    response.setCanUserPassTest(false);
                });

        testRateRepository.findByUser_UsernameAndTest_Id(username, test.getId())
                .ifPresent(it -> response.setRate(it.getRate()));

        return response;
    }

    public TestResultResponse toResultResponse(TestResult test) {
        var response = modelMapper.map(test, TestResultResponse.class);
        response.setNumberOfCorrectAnswers(response.getQuestions().stream().filter(QuestionResultResponse::isCorrect).count());
        return response;
    }
}
