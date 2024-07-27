package com.blimp.backend.service.impl;

import com.blimp.backend.service.ValidationService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    @Override
    public void validate(Object request) {
        var constraintViolations = validator.validate(request);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

}
