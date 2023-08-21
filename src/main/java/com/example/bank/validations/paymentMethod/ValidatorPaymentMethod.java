package com.example.bank.validations.paymentMethod;

import com.example.bank.enums.TransactionEnum;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class ValidatorPaymentMethod implements ConstraintValidator<ValidPaymentMethod, TransactionEnum> {
    @Override
    public void initialize(ValidPaymentMethod constraintAnnotation) {
    }

    @Override
    public boolean isValid(TransactionEnum value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        }

        return Arrays.asList(TransactionEnum.values()).contains(value);
    }
}
