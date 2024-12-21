package com.example.personal_accounting.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PositiveIdValidator.class)
//@BaseValidator
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveId {
    String message() default "ID must be positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

