package com.example.personal_accounting.dto.LoanAndDeposit;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateLoanAndDepositDto {
    @Size(max = 100)
    private String title;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal principalAmount;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "100.0")
    private BigDecimal interestRate;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
