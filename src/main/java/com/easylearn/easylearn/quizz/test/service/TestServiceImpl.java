package com.easylearn.easylearn.quizz.test.service;

import com.easylearn.easylearn.core.exception.EntityNotFoundException;
import com.easylearn.easylearn.quizz.answer.dto.AnswerParam;
import com.easylearn.easylearn.quizz.answer.repository.entity.AnswerEntity;
import com.easylearn.easylearn.quizz.question.model.QuestionResult;
import com.easylearn.easylearn.quizz.question.repository.QuestionRepository;
import com.easylearn.easylearn.quizz.question.repository.entity.QuestionEntity;
import com.easylearn.easylearn.quizz.test.dto.AnswerTestParam;
import com.easylearn.easylearn.quizz.test.dto.SearchParam;
import com.easylearn.easylearn.quizz.test.dto.TestParam;
import com.easylearn.easylearn.quizz.test.model.Test;
import com.easylearn.easylearn.quizz.test.model.TestResult;
import com.easylearn.easylearn.quizz.test.repository.TestRepository;
import com.easylearn.easylearn.quizz.test.repository.converter.TestEntityConverter;
import com.easylearn.easylearn.quizz.test.service.converter.TestParamConverter;
import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Validated
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    private final CurrentUserService currentUserService;
    private final UserService userService;

    private final TestEntityConverter testEntityConverter;
    private final TestParamConverter testParamConverter;

    @Override
    @Transactional(readOnly = true)
    @NotNull
    public Test loadOne(@NotNull Long id) {
        log.info("Load by id: {}", id);
        return testRepository.findById(id)
                .map(testEntityConverter::toModel)
                .orElseThrow();
    }

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
                .findByLanguageAndNameContainsAndUser_UsernameNotAndPublicTestTrueOrderByName(currentUserLanguage, search, currentUser);

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
        test.setCreatedAt(Instant.now());

        testRepository.save(testEntityConverter.toEntity(test));

        log.debug("Test has been created");
    }

    @Override
    @Transactional
    public void update(@NotNull Long id, @NotNull @Valid TestParam testParam) {
        log.info("Update test: {}", id);

        validateAnswers(testParam);
        var test = loadOne(id);
        testParamConverter.toUpdateModel(test, testParam);

        testRepository.save(testEntityConverter.toEntity(test));

        log.debug("Test has been updated");
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id) {
        log.info("Delete test: {}", id);

        testRepository.deleteById(id);

        log.debug("Test has been deleted");
    }

    @Override
    @Transactional(readOnly = true)
    public TestResult checkTest(@NotNull Long id, @NotNull @Valid AnswerTestParam testParam) {
        log.info("Validate answers for test {}", id);

        var result = testParam.getQuestions().stream()
                .map(it -> {
                    var questionId = it.getId();
                    var correctAnswerIds = getQuestionEntity(questionId).getAnswers().stream()
                            .filter(AnswerEntity::isCorrectAnswer).map(AnswerEntity::getId).collect(Collectors.toSet());

                    var correct = CollectionUtils.isEqualCollection(correctAnswerIds, it.getAnswers());

                    return new QuestionResult(questionId, correct);
                }).collect(Collectors.toSet());

        return new TestResult(id, result);
    }

    private QuestionEntity getQuestionEntity(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException(QuestionEntity.class.getSimpleName(), questionId));
    }

    private void validateAnswers(TestParam testParam) {
        var hasTestQuestionWithoutCorrectAnswers = testParam.getQuestions().stream().anyMatch(it -> it.getAnswers().stream().noneMatch(AnswerParam::getCorrectAnswer));
        if (hasTestQuestionWithoutCorrectAnswers) {
            throw new ValidationException("Вопрос должен содержать хотя бы один первый ответ.");
        }

        var hasQuestionNotFourAnswers = testParam.getQuestions().stream().anyMatch(it -> it.getAnswers().size() != 4);
        if (hasQuestionNotFourAnswers) {
            throw new ValidationException("Вопрос должен содержать 4 варианта ответа.");
        }
    }
}
