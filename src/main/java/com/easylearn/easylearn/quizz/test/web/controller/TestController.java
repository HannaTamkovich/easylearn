package com.easylearn.easylearn.quizz.test.web.controller;

import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TestController {

    public static final String TEST_PATH = "/tests";

    private final TestService testService;

    @PostMapping(TEST_PATH)
    public void create(@Valid @NotNull @RequestBody TestParam testParam) {
        testService.create(testParam);
    }
}
