package com.example.personal_accounting.utils.validators;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodicTransactionValidator implements ConstraintValidator<ValidPeriodicTransaction, CreateTransactionDto> {

    @Override
    public boolean isValid(CreateTransactionDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        boolean isPeriodic = dto.getIsPeriodic();
        Integer repeatInterval = dto.getRepeatInterval();

        if (isPeriodic) {
            if (repeatInterval == null || repeatInterval <= 0) {
                addConstraintViolation(context, "Repeat interval must be provided and positive for periodic transactions", "repeatInterval");
                return false;
            }
        }

        if (!isPeriodic && repeatInterval != null) {
            addConstraintViolation(context, "Repeat interval must be null for non-periodic transactions", "repeatInterval");
            return false;
        }

        return true;
    }
    private void addConstraintViolation(ConstraintValidatorContext context, String message, String property) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }
}

