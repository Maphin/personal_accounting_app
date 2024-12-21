package com.example.personal_accounting.validations;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BasicTransactionValidator implements TransactionValidator {
    @Override
    public void validate(CreateTransactionDto transactionDto) {
        if (transactionDto.getAmount() == null || transactionDto.getAmount().compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (transactionDto.getTransactionDate() == null) {
            throw new IllegalArgumentException("Date is required");
        }
        if (transactionDto.getAccountId() == null) {
            throw new IllegalArgumentException("Account ID is required");
        }
        if (transactionDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required");
        }
    }
}

