package com.easylearn.easylearn.quizz.test.service;

import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.repository.TestRepository;
import com.easylearn.easylearn.quizz.test.repository.converter.TestEntityConverter;
import com.easylearn.easylearn.quizz.test.service.converter.TestParamConverter;
import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final CurrentUserService currentUserService;
    private final UserService userService;

    private final TestEntityConverter testEntityConverter;
    private final TestParamConverter testParamConverter;

    @Override
    @Transactional
    public void create(@NotNull @Valid TestParam testParam) {
        log.info("Create test");

        var test = testParamConverter.toModel(testParam);

        var currentUser = userService.loadByUsername(currentUserService.getUsername());
        test.setUser(currentUser);
        test.setLanguage(currentUser.getLanguage());

        testRepository.save(testEntityConverter.toEntity(test));

        log.debug("Test has been created");
    }
}
