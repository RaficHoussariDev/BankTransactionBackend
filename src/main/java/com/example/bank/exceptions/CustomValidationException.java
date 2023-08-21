package com.example.bank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomValidationException extends RuntimeException {
    private List<String> errorMessages;
    private HttpStatus httpStatus;
}
