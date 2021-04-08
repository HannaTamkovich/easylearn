package com.easylearn.easylearn.quizz.test.service;

import com.easylearn.easylearn.quizz.test.dto.SearchParam;
import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.model.Test;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface TestService {

    @NotNull
    Collection<Test> findAll(@NotNull @Valid SearchParam searchParam);

    void create(@NotNull @Valid TestParam testParam);
}
