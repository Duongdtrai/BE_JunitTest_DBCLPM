package com.example.junit_test.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<ValidInteger, Integer> {

    @Override
    public void initialize(ValidInteger constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        System.out.println("value: " + value);
        return value == null || value % 1 == 0;
    }
}
