package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.dto.Account.CreateAccountDto;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.services.Accounts.AccountService;
import com.example.personal_accounting.utils.validators.PositiveId;
import jakarta.validation.Valid;
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
@RequestMapping(path ="/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid CreateAccountDto accountDto, @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) {
        Long userId = principal.userId();
        AccountDto createdAccount = accountService.create(accountDto, userId);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable @PositiveId Long id) throws AccountNotFoundException {
        Account account = accountService.getById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Account>> getUserAccounts(@AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) {
        Long userId = principal.userId();
        List<Account> accounts = accountService.getUserAccounts(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable @PositiveId Long id) throws AccountNotFoundException {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
