package com.easylearn.easylearn.word.useranswer.service;

import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.word.useranswer.model.WordUserAnswer;
import com.easylearn.easylearn.word.useranswer.repository.WordUserAnswerRepository;
import com.easylearn.easylearn.word.useranswer.repository.converter.WordUserAnswerEntityConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Service
public class WordUserAnswerServiceImpl implements WordUserAnswerService {

    private final CurrentUserService currentUserService;
    private final WordUserAnswerRepository wordUserAnswerRepository;
    private final WordUserAnswerEntityConverter wordUserAnswerEntityConverter;

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public WordUserAnswer loadStatistic() {
        var username = currentUserService.getUsername();
        return wordUserAnswerRepository.findByUser_Username(username)
                .map(wordUserAnswerEntityConverter::toModel)
                .orElseThrow();
    }
}
