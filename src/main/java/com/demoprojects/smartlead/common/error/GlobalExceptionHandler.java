package com.demoprojects.smartlead.common.error;

import com.demoprojects.smartlead.common.error.exceptions.DuplicatedMessageException;
import com.demoprojects.smartlead.common.error.exceptions.LeadNotFoundException;
import com.demoprojects.smartlead.common.error.exceptions.UserMessageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex,
                                                    HttpServletRequest request) {
        log.warn("Handled DuplicatedMessageException at {}", request.getRequestURI(), ex);

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed!",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(LeadNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(LeadNotFoundException ex,
                                                   HttpServletRequest request) {
        log.warn("Handled LeadNotFoundException at {}", request.getRequestURI(), ex);

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserMessageNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(UserMessageNotFoundException ex,
                                                   HttpServletRequest request) {
        log.warn("Handled UserMessageNotFoundException at {}", request.getRequestURI(), ex);

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedMessageException.class)
    public ResponseEntity<ApiError> handleDuplicates(DuplicatedMessageException ex,
                                                    HttpServletRequest request) {
        log.warn("Handled DuplicatedMessageException at {}", request.getRequestURI(), ex);

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception at {}", request.getRequestURI(), ex);

        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred.",
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
