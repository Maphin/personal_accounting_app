package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.dto.LoanAndDeposit.CreateLoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.LoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.PartialUpdateLoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.TotalAmountDto;
import com.example.personal_accounting.services.LoanAndDeposit.LoanAndDepositService;
import com.example.personal_accounting.utils.validators.PositiveId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<LoanAndDepositDto> getLoanOrDeposit(@PathVariable @PositiveId Long id) {
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
    public ResponseEntity<LoanAndDepositDto> partialUpdateLoanOrDeposit(
            @PathVariable @PositiveId Long id,
            @RequestBody @Valid PartialUpdateLoanAndDepositDto dto
    ) {
        LoanAndDepositDto updatedLoanOrDeposit = loanAndDepositService.update(id, dto);
        return new ResponseEntity<>(updatedLoanOrDeposit, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanOrDeposit(@PathVariable @PositiveId Long id) {
        loanAndDepositService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/calculate")
    public ResponseEntity<TotalAmountDto> calculateTotalAmount(@PathVariable Long id) {
        TotalAmountDto totalAmount = loanAndDepositService.calculateTotalAmount(id);
        return ResponseEntity.ok(totalAmount);
    }
}
