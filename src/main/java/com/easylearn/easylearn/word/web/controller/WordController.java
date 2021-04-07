package com.easylearn.easylearn.word.web.controller;

import com.easylearn.easylearn.core.dto.response.PageResult;
import com.easylearn.easylearn.word.dto.CardFilter;
import com.easylearn.easylearn.word.dto.WordFilter;
import com.easylearn.easylearn.word.dto.WordParam;
import com.easylearn.easylearn.word.service.WordService;
import com.easylearn.easylearn.word.web.converter.CardWebConverter;
import com.easylearn.easylearn.word.web.converter.WordWebConverter;
import com.easylearn.easylearn.word.web.dto.AnswerResponse;
import com.easylearn.easylearn.word.web.dto.CardResponse;
import com.easylearn.easylearn.word.web.dto.WordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class WordController {

    public static final String WORD_PATH = "/words";
    public static final String CARD_PATH = "/cards";
    public static final String PATH_BY_ID = WORD_PATH + "/{id}";
    public static final String PATH_ANSWER = WORD_PATH + "/{id}/answer";
    public static final String PATH_EMPTY_CATEGORY = WORD_PATH + "/empty-category";
    public static final String PATH_REMOVE_FROM_CATEGORY = WORD_PATH + "/{id}/remove-from-category/{categoryId}";
    public static final String PATH_ADD_TO_DICTIONARY = WORD_PATH + "/{id}/add-to-dictionary";

    private final WordService wordService;
    private final WordWebConverter wordWebConverter;
    private final CardWebConverter cardWebConverter;

    @GetMapping(PATH_BY_ID)
    public WordResponse findById(@NotNull @PathVariable("id") Long id) {
        var category = wordService.findById(id);
        return wordWebConverter.toResponse(category);
    }

    @GetMapping(WORD_PATH)
    public Collection<WordResponse> findAll(@NotNull @Valid WordFilter wordFilter) {
        var words = wordService.findAll(wordFilter);
        return wordWebConverter.toResponses(words, wordFilter.getUsername());
    }

    @GetMapping(CARD_PATH)
    public PageResult<CardResponse> findAllCards(@NotNull @Valid CardFilter cardFilter) {
        var cards = wordService.findAllCards(cardFilter);
        return cardWebConverter.toResponse(cards);
    }

    @PostMapping(WORD_PATH)
    public void create(@Valid @NotNull @RequestBody WordParam wordParam) {
        wordService.create(wordParam);
    }

    @PutMapping(PATH_BY_ID)
    public void update(@NotNull @PathVariable("id") Long id, @Valid @NotNull @RequestBody WordParam wordParam) {
        wordService.update(id, wordParam);
    }

    @DeleteMapping(PATH_BY_ID)
    public void delete(@NotNull @PathVariable("id") Long id) {
        wordService.delete(id);
    }

    @GetMapping(PATH_ANSWER)
    public AnswerResponse answer(@NotNull @PathVariable("id") Long id, @NotBlank @PathParam(value = "selectedValue") String selectedValue) {
        var isCorrectAnswer = wordService.answer(id, selectedValue);
        return new AnswerResponse(id, selectedValue, isCorrectAnswer);
    }

    @GetMapping(PATH_EMPTY_CATEGORY)
    public Collection<WordResponse> findAllWithEmptyCategory() {
        var words = wordService.findAllWithEmptyCategory();
        return wordWebConverter.toBaseResponses(words);
    }

    @DeleteMapping(PATH_REMOVE_FROM_CATEGORY)
    public void deleteFromCategory(@NotNull @PathVariable("id") Long id, @NotNull @PathVariable("categoryId") Long categoryId) {
        wordService.deleteFromCategory(id, categoryId);
    }

    @PostMapping(PATH_ADD_TO_DICTIONARY)
    public void addToDictionary(@NotNull @PathVariable("id") Long id) {
        wordService.addToUserWords(id);
    }
}
