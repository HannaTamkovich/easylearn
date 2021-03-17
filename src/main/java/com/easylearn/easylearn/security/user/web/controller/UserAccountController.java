package com.easylearn.easylearn.security.user.web.controller;

import com.easylearn.easylearn.core.dto.response.ErrorResponse;
import com.easylearn.easylearn.security.service.CurrentUserService;
import com.easylearn.easylearn.security.user.service.UserAccountService;
import com.easylearn.easylearn.security.user.web.converter.UserAccountWebConverter;
import com.easylearn.easylearn.security.user.web.dto.UserAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@Validated
@AllArgsConstructor
@CrossOrigin
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountWebConverter userAccountWebConverter;
    private final CurrentUserService currentUserService;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserAccountResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    })
    @GetMapping
    public Collection<UserAccountResponse> findAll() {
        var users = userAccountService.findAll();
        return userAccountWebConverter.toResponses(users);
    }

    @Operation(summary = "Get information about me")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserAccountResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content())
    })
    @GetMapping("/me")
    public UserAccountResponse me() {
        var user = userAccountService.loadByUsername(currentUserService.getUsername());
        return userAccountWebConverter.toResponse(user);
    }

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserAccountResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{username}")
    public UserAccountResponse read(@NotNull @PathVariable String username) {
        var user = userAccountService.loadByUsername(username);
        return userAccountWebConverter.toResponse(user);
    }
}
