package com.example.personal_accounting.dto.Account;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountDto {
    @NotBlank(message = "Account title is required")
    private String title;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", message = "Balance must be zero or positive")
    private BigDecimal balance;
}
