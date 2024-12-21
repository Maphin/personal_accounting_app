package com.example.personal_accounting.utils.validators;

import com.example.personal_accounting.types.TransactionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionTypeValidator implements ConstraintValidator<ValidTransactionType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            TransactionType transactionType = TransactionType.valueOf(value);
            return transactionType != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
