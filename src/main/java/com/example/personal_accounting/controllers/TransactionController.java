package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import com.example.personal_accounting.models.Transaction;
import com.example.personal_accounting.services.Transactions.TransactionService;
import com.example.personal_accounting.utils.validators.PositiveId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Validated
@RestController
@RequestMapping(path ="/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody @Valid CreateTransactionDto transactionDto,
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) throws AccountNotFoundException {
        Transaction transaction = transactionService.createTransaction(transactionDto, principal.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/my")
    public ResponseEntity<List<Transaction>> getAllUserTransactions(@AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) {
        Long userId = principal.userId();
        List<Transaction> transactions = transactionService.getUserTransactions(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategoryId(@PathVariable Long categoryId) {
        List<Transaction> transactions = transactionService.getTransactionsByCategoryId(categoryId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/periodic")
    public ResponseEntity<List<Transaction>> getPeriodicTransactions(@RequestParam boolean isPeriodic) {
        List<Transaction> transactions = transactionService.getPeriodicTransactions(isPeriodic);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable @PositiveId Long id,
                                                  @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) throws AccountNotFoundException {
        Long userId = principal.userId();
        transactionService.deleteTransaction(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
