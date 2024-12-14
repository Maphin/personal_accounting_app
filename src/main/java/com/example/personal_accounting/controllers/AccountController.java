package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.dto.Account.CreateAccountDto;
import com.example.personal_accounting.services.Accounts.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
