package com.easylearn.easylearn.quizz.test.service;

import com.easylearn.easylearn.quizz.test.dto.AnswerTestParam;
import com.easylearn.easylearn.quizz.test.dto.SearchParam;
import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.quizz.test.model.TestResult;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface TestService {

    @NotNull
    Test loadOne(@NotNull Long id);

    @NotNull
    Collection<Test> findAll(@NotNull @Valid SearchParam searchParam);

    void create(@NotNull @Valid TestParam testParam);

    void update(@NotNull Long id, @NotNull @Valid TestParam testParam);

    void delete(@NotNull Long id);

    @NotNull
    TestResult checkTest(@NotNull Long id, @NotNull @Valid AnswerTestParam testParam);
}
