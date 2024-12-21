package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.dto.LoanAndDeposit.CreateLoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.LoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.UpdateLoanAndDepositDto;
import com.example.personal_accounting.services.LoanAndDeposit.LoanAndDepositService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans-deposits")
@RequiredArgsConstructor
public class LoanAndDepositController {

    private final LoanAndDepositService loanAndDepositService;

    @PostMapping
    public ResponseEntity<LoanAndDepositDto> createLoanOrDeposit(
            @RequestBody @Valid CreateLoanAndDepositDto dto,
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal
    ) {
        Long userId = principal.userId();
        LoanAndDepositDto createdLoanOrDeposit = loanAndDepositService.create(dto, userId);
        return new ResponseEntity<>(createdLoanOrDeposit, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanAndDepositDto> getLoanOrDeposit(@PathVariable Long id) {
        LoanAndDepositDto loanOrDeposit = loanAndDepositService.getById(id);
        return new ResponseEntity<>(loanOrDeposit, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LoanAndDepositDto>> getAllLoansAndDeposits(
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal
    ) {
        Long userId = principal.userId();
        List<LoanAndDepositDto> loansAndDeposits = loanAndDepositService.getAllByUser(userId);
        return new ResponseEntity<>(loansAndDeposits, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LoanAndDepositDto> updateLoanOrDeposit(
            @PathVariable Long id,
            @RequestBody @Valid UpdateLoanAndDepositDto dto
    ) {
        LoanAndDepositDto updatedLoanOrDeposit = loanAndDepositService.update(id, dto);
        return new ResponseEntity<>(updatedLoanOrDeposit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanOrDeposit(@PathVariable Long id) {
        loanAndDepositService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
