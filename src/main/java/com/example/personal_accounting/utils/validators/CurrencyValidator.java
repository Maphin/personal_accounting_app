package com.example.personal_accounting.utils.validators;

import com.example.personal_accounting.types.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            Currency currency = Currency.valueOf(value);
            return currency != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
