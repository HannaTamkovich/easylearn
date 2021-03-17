package com.easylearn.easylearn.language.web.controller;

import com.easylearn.easylearn.language.model.Language;
import com.easylearn.easylearn.language.web.converter.LanguageWebConverter;
import com.easylearn.easylearn.language.web.dto.LanguageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Get all languages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = LanguageResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    })
    @GetMapping
    public Collection<LanguageResponse> findAll() {
        var languages = Arrays.asList(Language.values());
        return languageWebConverter.toResponses(languages);
    }
}
