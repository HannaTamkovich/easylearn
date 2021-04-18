package com.easylearn.easylearn.word.useranswer.service;

import com.easylearn.easylearn.word.useranswer.model.WordUserAnswer;
import com.sun.istack.NotNull;

public interface WordUserAnswerService {

    @NotNull
    WordUserAnswer loadStatistic();
}
