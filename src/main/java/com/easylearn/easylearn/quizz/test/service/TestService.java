package com.easylearn.easylearn.quizz.test.service;

import com.easylearn.easylearn.quizz.test.dto.TestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface TestService {

    void create(@NotNull @Valid TestParam testParam);
}
