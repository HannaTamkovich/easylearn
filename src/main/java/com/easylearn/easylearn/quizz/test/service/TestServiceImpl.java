package com.easylearn.easylearn.quizz.test.service;

import com.easylearn.easylearn.quizz.answer.dto.AnswerParam;
import com.easylearn.easylearn.quizz.test.dto.SearchParam;
import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.quizz.test.repository.TestRepository;
import com.easylearn.easylearn.quizz.test.repository.converter.TestEntityConverter;
import com.easylearn.easylearn.quizz.test.service.converter.TestParamConverter;
import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

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
    @Transactional(readOnly = true)
    @NotNull
    public Collection<Test> findAll(@NotNull @Valid SearchParam searchParam) {
        log.info("Find all tests");

        var search = Optional.ofNullable(searchParam.getSearch()).orElse(StringUtils.EMPTY);
        var currentUser = currentUserService.getUsername();

        if (searchParam.isOnlyUserTests()) {
            var testEntities = testRepository.findByUser_UsernameAndNameContainsOrderByName(currentUser, search);

            return testEntityConverter.toModels(testEntities);
        }

        var currentUserLanguage = currentUserService.getLanguage();
        var testEntities = testRepository
                .findByLanguageAndNameContainsAndUser_UsernameNotAndIsPublicTestTrueOrderByName(currentUserLanguage, currentUser, search);

        return testEntityConverter.toModels(testEntities);
    }

    @Override
    @Transactional
    public void create(@NotNull @Valid TestParam testParam) {
        log.info("Create test");

        validateAnswers(testParam);

        var test = testParamConverter.toModel(testParam);

        var currentUser = userService.loadByUsername(currentUserService.getUsername());
        test.setUser(currentUser);
        test.setLanguage(currentUser.getLanguage());

        testRepository.save(testEntityConverter.toEntity(test));

        log.debug("Test has been created");
    }

    private void validateAnswers(TestParam testParam) {
        var hasTestQuestionWithoutCorrectAnswers = testParam.getQuestions().stream().anyMatch(it -> it.getAnswers().stream().noneMatch(AnswerParam::getIsCorrectAnswer));
        if (hasTestQuestionWithoutCorrectAnswers) {
            throw new ValidationException("Вопрос должен содержать хотя бы один первый ответ.");
        }

        var hasQuestionNotFourAnswers = testParam.getQuestions().stream().anyMatch(it -> it.getAnswers().size() != 4);
        if (hasQuestionNotFourAnswers) {
            throw new ValidationException("Вопрос должен содержать 4 варианта ответа.");
        }
    }
}
