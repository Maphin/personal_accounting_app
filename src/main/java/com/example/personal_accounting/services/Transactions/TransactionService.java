package com.example.personal_accounting.services.Transactions;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import com.example.personal_accounting.services.ExpenseCategory.ExpenseCategoryService;
import com.example.personal_accounting.types.TransactionType;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.Transaction;
import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.repository.TransactionRepository;
import com.example.personal_accounting.services.Accounts.AccountService;
import com.example.personal_accounting.utils.CheckPermissions;
import com.example.personal_accounting.utils.exceptions.TransactionNotFoundException;
import com.example.personal_accounting.validations.TransactionValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;
    private final AccountService accountService;
    private final ExpenseCategoryService expenseCategoryService;

    @Transactional
    public Transaction createTransaction(CreateTransactionDto transactionDto, Long userId) throws AccountNotFoundException {
        transactionValidator.validate(transactionDto);

        Account account = accountService.getById(transactionDto.getAccountId());

        CheckPermissions.checkPermissions(account.getUser().getId(), userId, "You do not have permissions to create a transaction within this account");

        ExpenseCategory category = expenseCategoryService.getCategoryById(transactionDto.getCategoryId());
        TransactionType transactionType = TransactionType.valueOf(transactionDto.getTransactionType());

        if (TransactionType.WITHDRAW.equals(transactionType)) {
            accountService.withdraw(account.getId(), transactionDto.getAmount());
        } else if (TransactionType.DEPOSIT.equals(transactionType)) {
            accountService.deposit(account.getId(), transactionDto.getAmount());
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setExpenseCategory(category);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionType);
        transaction.setDescription(transactionDto.getDescription());
        transaction.setTransactionDate(transactionDto.getTransactionDate());
        transaction.setPeriodic(transactionDto.isPeriodic());
        transaction.setRepeatInterval(transactionDto.getRepeatInterval());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + id));
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getUserTransactions(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
    public List<Transaction> getTransactionsByCategoryId(Long categoryId) {
        return transactionRepository.findByExpenseCategoryId(categoryId);
    }
    public List<Transaction> getPeriodicTransactions(boolean isPeriodic) {
        return transactionRepository.findByIsPeriodic(isPeriodic);
    }

    @Transactional
    public void deleteTransaction(Long id, Long userId) throws AccountNotFoundException, TransactionNotFoundException {

        Transaction transaction = getTransactionById(id);
        Account account = accountService.getById(transaction.getAccount().getId());

        CheckPermissions.checkPermissions(account.getUser().getId(), userId,"You do not have permissions to delete this transaction");

        if (TransactionType.DEPOSIT.equals(transaction.getTransactionType())) {
            accountService.deposit(account.getId(), transaction.getAmount());
        } else if (TransactionType.WITHDRAW.equals(transaction.getTransactionType())) {
           accountService.withdraw(account.getId(), transaction.getAmount());
        }

        transactionRepository.delete(transaction);
    }
}
