package com.example.personal_accounting.dto.LoanAndDeposit;

import com.example.personal_accounting.types.Type;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateLoanAndDepositDto {
    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    private Type type;

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
