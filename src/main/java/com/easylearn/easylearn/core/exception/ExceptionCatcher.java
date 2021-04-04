package com.easylearn.easylearn.core.exception;

import com.easylearn.easylearn.core.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Slf4j
public class ExceptionCatcher {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException ex) {
        var error = ErrorResponse.of(NOT_FOUND, "Entity not found", ex.getMessage());
        log.warn("EntityNotFoundException was thrown", ex);
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(com.easylearn.easylearn.core.exception.EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(com.easylearn.easylearn.core.exception.EntityNotFoundException ex) {
        var error = ErrorResponse.of(NOT_FOUND, "Entity not found", ex.getMessage());
        log.warn("EntityNotFoundException was thrown", ex);
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        var error = ErrorResponse.of(FORBIDDEN, "Access denied", ex.getMessage());
        log.debug("AccessDeniedException was thrown", ex);
        return new ResponseEntity<>(error, FORBIDDEN);
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        var error = ErrorResponse.of(BAD_REQUEST, "Validation error", ex.getMessage());
        log.warn("ValidationException was thrown", ex);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> duplicateException(DuplicateException ex) {
        var error = ErrorResponse.of(CONFLICT, "Duplicate operation", ex.getMessage());
        log.warn("DuplicateException was thrown", ex);
        return new ResponseEntity<>(error, CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundException(AuthenticationException ex) {
        var error = ErrorResponse.of(UNAUTHORIZED, "Authentication exception", ex.getMessage());
        log.warn("UsernameNotFoundException was thrown", ex);
        return new ResponseEntity<>(error, UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsException(AuthenticationException ex) {
        var error = ErrorResponse.of(UNAUTHORIZED, "Authentication exception", ex.getMessage());
        log.warn("BadCredentialsException was thrown", ex);
        return new ResponseEntity<>(error, UNAUTHORIZED);
    }
}
