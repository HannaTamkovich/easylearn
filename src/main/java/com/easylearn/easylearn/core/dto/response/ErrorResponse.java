package com.easylearn.easylearn.core.dto.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.beans.ConstructorProperties;
import java.time.Instant;

@Getter
@ToString(of = {"status", "message"})
public class ErrorResponse {

    private final HttpStatus status;
    private final Instant timestamp;
    private final String message;
    private final String details;

    @ConstructorProperties({"code", "message", "details"})
    private ErrorResponse(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = Instant.now();
    }

    public static ErrorResponse of(HttpStatus status, String message, String details) {
        return new ErrorResponse(status, message, details);
    }
}
