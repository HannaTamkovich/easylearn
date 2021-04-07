package com.easylearn.easylearn.language.web.controller;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.language.web.converter.LanguageWebConverter;
import com.easylearn.easylearn.language.web.dto.LanguageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(LanguageController.LANGUAGES_PATH)
@CrossOrigin
public class LanguageController {

    public static final String LANGUAGES_PATH = "/languages";

    private final LanguageWebConverter languageWebConverter;

    @GetMapping
    public Collection<LanguageResponse> findAll() {
        var languages = Arrays.asList(Language.values());
        return languageWebConverter.toResponses(languages);
    }
}
