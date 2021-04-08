package com.easylearn.easylearn.quizz.test.web.controller;

import com.easylearn.easylearn.quizz.test.dto.SearchParam;
import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.service.TestService;
import com.easylearn.easylearn.quizz.test.web.converter.TestWebConverter;
import com.easylearn.easylearn.quizz.test.web.dto.ListOfTestsResponse;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    //TODO дата создания
    //TODO отдать тест на прохождение
    //TODO проверить тест
    //TODO оценка теста
    //TODO кол-во прохождений

    public static final String TEST_PATH = "/tests";

    private final TestService testService;
    private final TestWebConverter testWebConverter;

    @GetMapping(TEST_PATH)
    public Collection<ListOfTestsResponse> findAll(@Nullable @RequestParam String search, @NotNull @RequestParam boolean onlyUserTests) {
        var tests = testService.findAll(new SearchParam(onlyUserTests, search));
        return testWebConverter.toListResponses(tests);
    }

    @PostMapping(TEST_PATH)
    public void create(@Valid @NotNull @RequestBody TestParam testParam) {
        testService.create(testParam);
    }
}
