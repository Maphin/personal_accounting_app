package com.example.personal_accounting.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeriodicTransactionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeriodicTransaction {
    String message() default "Invalid periodic transaction data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
