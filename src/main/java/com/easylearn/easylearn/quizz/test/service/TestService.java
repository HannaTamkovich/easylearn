package com.easylearn.easylearn.quizz.test.service;

import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.sun.istack.NotNull;

import javax.validation.Valid;

public interface TestService {

    void create(@NotNull @Valid TestParam testParam);
}
