package com.easylearn.easylearn.quizz.test.web.controller;

import com.easylearn.easylearn.quizz.test.dto.AnswerTestParam;
import com.easylearn.easylearn.quizz.test.dto.RatingParam;
import com.easylearn.easylearn.quizz.test.dto.SearchParam;
import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.service.TestService;
import com.easylearn.easylearn.quizz.test.web.converter.TestWebConverter;
import com.easylearn.easylearn.quizz.test.web.dto.ListOfTestsResponse;
import com.easylearn.easylearn.quizz.test.web.dto.PassTestResponse;
import com.easylearn.easylearn.quizz.test.web.dto.TestResponse;
import com.easylearn.easylearn.quizz.test.web.dto.TestResultResponse;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TestController {

    public static final String TEST_PATH = "/tests";
    public static final String BY_ID_PATH = "/tests/{id}";
    public static final String PASS_TEST_PATH = "/tests/{id}/pass";
    public static final String PASS_TEST_RESULT_PATH = "/tests/{id}/pass/result";
    public static final String RATING_PATH = "/tests/{id}/rating";

    private final TestService testService;
    private final TestWebConverter testWebConverter;

    @GetMapping(BY_ID_PATH)
    public TestResponse findById(@NotNull @PathVariable Long id) {
        var test = testService.loadOne(id);
        return testWebConverter.toResponse(test);
    }

    @GetMapping(PASS_TEST_PATH)
    public PassTestResponse findByIdForPass(@NotNull @PathVariable Long id) {
        var test = testService.loadOne(id);
        return testWebConverter.toPassResponse(test);
    }

    @GetMapping(TEST_PATH)
    public Collection<ListOfTestsResponse> findAll(@Nullable @RequestParam String search, @NotNull @RequestParam boolean onlyUserTests) {
        var tests = testService.findAll(new SearchParam(onlyUserTests, search));
        return testWebConverter.toListResponses(tests);
    }

    @PostMapping(TEST_PATH)
    public void create(@Valid @NotNull @RequestBody TestParam testParam) {
        testService.create(testParam);
    }

    @PutMapping(BY_ID_PATH)
    public void update(@NotNull @PathVariable Long id, @Valid @NotNull @RequestBody TestParam testParam) {
        testService.update(id, testParam);
    }

    @DeleteMapping(BY_ID_PATH)
    public void delete(@NotNull @PathVariable Long id) {
        testService.delete(id);
    }

    @PostMapping(PASS_TEST_RESULT_PATH)
    public TestResultResponse checkTest(@NotNull @PathVariable Long id, @Valid @NotNull @RequestBody AnswerTestParam testParam) {
        var checked = testService.checkTest(id, testParam);
        return testWebConverter.toResultResponse(checked);
    }

    @PostMapping(RATING_PATH)
    public void ratingTest(@NotNull @PathVariable Long id, @Valid @NotNull @RequestBody RatingParam rating) {
        testService.ratingTest(id, rating.getRating());
    }
}
