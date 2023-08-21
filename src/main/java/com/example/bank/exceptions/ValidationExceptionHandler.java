package com.example.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<Object> handleValidationException(CustomValidationException exception, WebRequest webRequest) {

        Map<String, Object> body = new HashMap<>();
        body.put("messages", exception.getErrorMessages());
        body.put("status", exception.getHttpStatus());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
