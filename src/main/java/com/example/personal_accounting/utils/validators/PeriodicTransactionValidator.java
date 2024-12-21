package com.example.personal_accounting.utils.validators;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodicTransactionValidator implements ConstraintValidator<ValidPeriodicTransaction, CreateTransactionDto> {
    @Override
    public boolean isValid(CreateTransactionDto dto, ConstraintValidatorContext context) {
        if (dto.isPeriodic() && (dto.getRepeatInterval() == null || dto.getRepeatInterval() <= 0)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Repeat interval must be provided and positive for periodic transactions")
                    .addPropertyNode("repeatInterval")
                    .addConstraintViolation();
            return false;
        } else if (!dto.isPeriodic() && dto.getRepeatInterval() != null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Repeat interval must be null for non-periodic transactions")
                    .addPropertyNode("repeatInterval")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
