package com.demoprojects.smartlead.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException ex,
                                                    HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        ApiError apiError = new ApiError(
                request.getContextPath(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                ex.getBindingResult().getFieldErrors().stream().map(FieldError::toString).toList()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedMessageException.class)
    public ResponseEntity<ApiError> handleException(DuplicatedMessageException ex,
                                                    HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        ApiError apiError = new ApiError(
                request.getContextPath(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                new ArrayList<>());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(
                request.getContextPath(),
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ZonedDateTime.now(),
                new ArrayList<>()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
