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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
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

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        var error = ErrorResponse.of(BAD_REQUEST, "Constraint violations", ex.getMessage());
        log.warn("ConstraintViolationException was thrown", ex);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        var error = ErrorResponse.of(BAD_REQUEST, "Validation error", ex.getMessage());
        log.warn("ValidationException was thrown", ex);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException ex) {
        var error = ErrorResponse.of(BAD_REQUEST, "Illegal argument", ex.getMessage());
        log.warn("IllegalArgumentException was thrown", ex);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(EntityExistsException ex) {
        var error = ErrorResponse.of(BAD_REQUEST, "Already exists", ex.getMessage());
        log.warn("EntityExistsException was thrown", ex);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> duplicateException(DuplicateException ex) {
        var error = ErrorResponse.of(CONFLICT, "Duplicate operation", ex.getMessage());
        log.warn("Duplicate operation", ex);
        return new ResponseEntity<>(error, CONFLICT);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> duplicateException(AuthenticationException ex) {
        var error = ErrorResponse.of(UNAUTHORIZED, "Authentication exception", ex.getMessage());
        log.warn("Authentication exception", ex);
        return new ResponseEntity<>(error, UNAUTHORIZED);
    }
}
