package com.example.personal_accounting.services.Transactions;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import com.example.personal_accounting.exceptions.CategoryNotFoundException;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.Transaction;
import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.repository.AccountRepository;
import com.example.personal_accounting.repository.ExpenseCategoryRepository;
import com.example.personal_accounting.repository.TransactionRepository;
import com.example.personal_accounting.services.Accounts.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public Transaction createTransaction(CreateTransactionDto transactionDto) throws AccountNotFoundException {
        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        ExpenseCategory category = expenseCategoryRepository.findById(transactionDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (transactionDto.getAmount() < 0) {
            accountService.withdraw(account.getId(), transactionDto.getAmount());
        } else {
            accountService.deposit(account.getId(), transactionDto.getAmount());
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setExpenseCategory(category);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setDate(transaction.getDate());
        transaction.setCreatedAt(Instant.now());

        return transactionRepository.save(transaction);
    }
}
