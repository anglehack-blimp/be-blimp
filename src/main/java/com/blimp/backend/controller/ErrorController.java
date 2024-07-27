package com.blimp.backend.controller;

import com.blimp.backend.dto.BlimpResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BlimpResponse<Object>> responseStatusException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(new BlimpResponse<>(null, exception.getReason()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BlimpResponse<Object> constraintViolationException(ConstraintViolationException exception) {
        return new BlimpResponse<>(null, exception.getMessage());
    }

}
