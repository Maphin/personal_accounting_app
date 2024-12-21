package com.example.personal_accounting.dto.Transaction;

import com.example.personal_accounting.types.TransactionType;
import com.example.personal_accounting.utils.validators.ValidPeriodicTransaction;
import com.example.personal_accounting.utils.validators.ValidTransactionType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ValidPeriodicTransaction
public class CreateTransactionDto {
    @NotNull(message = "Account ID cannot be null")
    private Long accountId;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    //@NotNull(message = "Transaction type is required")
    @ValidTransactionType(message = "Transaction type is required and must be a valid enum value: WITHDRAW | DEPOSIT")
    private String transactionType;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotNull(message = "Transaction date cannot be null")
    @PastOrPresent(message = "Transaction date cannot be in the future")
    private LocalDate transactionDate;

    private boolean isPeriodic;

    @Min(value = 1, message = "Repeat interval must be at least 1 day")
    @Max(value = 365, message = "Repeat interval cannot exceed 365 days")
    @Null(message = "Repeat interval must be null if the transaction is not periodic")
    private Integer repeatInterval; // in days
}
