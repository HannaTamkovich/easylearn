package com.easylearn.easylearn.word.web.controller;

import com.easylearn.easylearn.category.web.dto.CategoryResponse;
import com.easylearn.easylearn.core.dto.response.ErrorResponse;
import com.easylearn.easylearn.core.dto.response.PageResult;
import com.easylearn.easylearn.word.dto.CardFilter;
import com.easylearn.easylearn.word.dto.WordFilter;
import com.easylearn.easylearn.word.dto.WordParam;
import com.easylearn.easylearn.word.service.WordService;
import com.easylearn.easylearn.word.web.converter.CardWebConverter;
import com.easylearn.easylearn.word.web.converter.WordWebConverter;
import com.easylearn.easylearn.word.web.dto.CardResponse;
import com.easylearn.easylearn.word.web.dto.WordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    private final WordService wordService;
    private final WordWebConverter wordWebConverter;
    private final CardWebConverter cardWebConverter;

    @Operation(summary = "Get word by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WordResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(PATH_BY_ID)
    public WordResponse findById(@NotNull @PathVariable("id") Long id) {
        var category = wordService.findById(id);
        return wordWebConverter.toResponse(category);
    }

    @Operation(summary = "Get all words")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WordResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(WORD_PATH)
    public Collection<WordResponse> findAll(@NotNull @Valid WordFilter wordFilter) {
        var words = wordService.findAll(wordFilter);
        return wordWebConverter.toResponses(words);
    }

    @Operation(summary = "Get all cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(CARD_PATH)
    public PageResult<CardResponse> findAllCards(@NotNull @Valid CardFilter cardFilter) {
        var cards = wordService.findAllCards(cardFilter);
        return cardWebConverter.toResponse(cards);
    }

    @Operation(summary = "Create word")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping(WORD_PATH)
    public void create(@Valid @NotNull @RequestBody WordParam wordParam) {
        wordService.create(wordParam);
    }

    @Operation(summary = "Update word by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping(PATH_BY_ID)
    public void update(@NotNull @PathVariable("id") Long id, @Valid @NotNull @RequestBody WordParam wordParam) {
        wordService.update(id, wordParam);
    }

    @Operation(summary = "Delete category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping(PATH_BY_ID)
    public void delete(@NotNull @PathVariable("id") Long id) {
        wordService.delete(id);
    }

    @Operation(summary = "Answer word")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(PATH_ANSWER)
    public boolean answer(@NotNull @PathVariable("id") Long id, @NotBlank @PathParam(value = "selectedValue") String selectedValue) {
        return wordService.answer(id, selectedValue);
    }

    @Operation(summary = "Get all words")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WordResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(PATH_EMPTY_CATEGORY)
    public Collection<WordResponse> findAllWithEmptyCategory() {
        var words = wordService.findAllWithEmptyCategory();
        return wordWebConverter.toEmptyResponses(words);
    }

}
