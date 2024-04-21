package com.example.junit_test.filter;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SystemResponse<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        System.out.println("Duogn213");
        exception.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            String[] fieldParts = field.split("\\.");
            String fieldName = fieldParts[fieldParts.length - 1];
            errorMap.put(fieldName, message);
        });
        return Response.badRequest(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", errorMap);
    }
}
