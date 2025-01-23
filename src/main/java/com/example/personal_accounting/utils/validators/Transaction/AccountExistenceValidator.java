package com.example.personal_accounting.utils.validators.Transaction;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import com.example.personal_accounting.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountExistenceValidator implements TransactionValidator {
    private final TransactionValidator wrappedValidator;
    private final AccountRepository accountRepository;

    public AccountExistenceValidator(TransactionValidator wrappedValidator, AccountRepository accountRepository) {
        this.wrappedValidator = wrappedValidator;
        this.accountRepository = accountRepository;
    }

    @Override
    public void validate(CreateTransactionDto transactionDto) {
        wrappedValidator.validate(transactionDto);

        if (!accountRepository.existsById(transactionDto.getAccountId())) {
            throw new IllegalArgumentException("Account does not exist");
        }
    }
}

