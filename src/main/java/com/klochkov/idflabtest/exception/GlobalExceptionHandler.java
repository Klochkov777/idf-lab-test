package com.klochkov.idflabtest.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Method handles MethodArgumentNotValidException.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Set<String> errorsSet = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errorsSet.add(errorMessage);
        });
        return buildErrorArrayResponse(errorsSet.toArray(new String[0]));
    }
    /**
     * Method handles ConstraintViolationException.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Set<String> errorsSet = new HashSet<>();
        ex.getConstraintViolations().forEach(error -> {
            String errorMessage = error.getMessage();
            errorsSet.add(errorMessage);
        });
        return buildErrorArrayResponse(errorsSet.toArray(new String[0]));
    }
    /**
     * Method handles ResourceNotFoundException.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleValueNotFoundException(ResourceNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }
    /**
     * Method for creating ErrorResponse.
     *
     * @param httpStatus - httpStatus of response
     * @param message - message of response
     */
    protected static ResponseEntity<Object> buildErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse response = new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase(), message);
        return ResponseEntity.status(httpStatus.value()).body(response);
    }
    /**
     * Method for creating ErrorResponse.
     *
     * @param message - message of response
     */
    protected static ResponseEntity<Object> buildErrorArrayResponse(String[] message) {
        ErrorArrayResponse response = new ErrorArrayResponse(
                HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class ErrorResponse {
        private int status;
        private String error;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class ErrorArrayResponse {
        private int status;
        private String error;
        private String[] message;
    }
}
