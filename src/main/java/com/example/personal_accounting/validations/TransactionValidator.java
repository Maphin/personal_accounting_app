package com.example.personal_accounting.validations;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;

public interface TransactionValidator {
    void validate(CreateTransactionDto transactionDto);
}