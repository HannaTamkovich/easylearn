package com.easylearn.easylearn.word.useranswer.web.controller;

import com.easylearn.easylearn.word.useranswer.service.WordUserAnswerService;
import com.easylearn.easylearn.word.useranswer.web.converter.WordUserWebConverter;
import com.easylearn.easylearn.word.useranswer.web.dto.WordUserAnswerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class WordUserController {

    public static final String WORD_STATISTIC_PATH = "/word-statistics";

    private final WordUserAnswerService wordUserAnswerService;
    private final WordUserWebConverter wordUserWebConverter;

    @GetMapping(WORD_STATISTIC_PATH)
    public WordUserAnswerResponse findWordStatistic() {
        var wordUserAnswer = wordUserAnswerService.loadStatistic();
        return wordUserWebConverter.toResponse(wordUserAnswer);
    }
}
