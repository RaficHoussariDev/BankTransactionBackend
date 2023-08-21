package com.example.bank.exceptions;

import com.example.bank.dtos.AccountDto;
import com.example.bank.dtos.CustomerDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DtoValidation {
    public List<String> validateCustomer(CustomerDto customerDto) {
        List<String> errors = new ArrayList<>();
        Validator validator = this.getValidator();
        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(customerDto);

        for (ConstraintViolation<CustomerDto> violation : violations) {
            errors.add(violation.getMessage());
        }

        return errors;
    }

    public List<String> validateAccount(AccountDto accountDto) {
        List<String> errors = new ArrayList<>();
        Validator validator = this.getValidator();
        Set<ConstraintViolation<AccountDto>> violations = validator.validate(accountDto);

        for (ConstraintViolation<AccountDto> violation : violations) {
            errors.add(violation.getMessage());
        }

        return errors;
    }

    private Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
