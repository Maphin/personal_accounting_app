package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.dto.Fund.AddMoneyToFundDto;
import com.example.personal_accounting.dto.Fund.CloseFundDto;
import com.example.personal_accounting.dto.Fund.CreateFundDto;
import com.example.personal_accounting.dto.Fund.FundDto;
import com.example.personal_accounting.models.Fund;
import com.example.personal_accounting.models.Transaction;
import com.example.personal_accounting.services.Fund.FundService;
import com.example.personal_accounting.services.Transactions.TransactionService;
import com.example.personal_accounting.utils.exceptions.FundNotFoundException;
import com.example.personal_accounting.utils.mappers.FundMapper;
import com.example.personal_accounting.utils.validators.PositiveId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping(path ="/funds")
@RequiredArgsConstructor
public class FundController {
    private final FundService fundService;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<FundDto> createFund(
            @RequestBody @Valid CreateFundDto dto,
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) {
        Long userId = principal.userId();
        FundDto createdFund = fundService.create(dto, userId);
        return new ResponseEntity<>(createdFund, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FundDto>> getAllFunds() {
        List<Fund> funds = fundService.getAll();
        List<FundDto> responseDtos = funds.stream()
                .map(FundMapper::toDto)
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
    @GetMapping("/my")
    public ResponseEntity<List<FundDto>> getAllUserFunds(@AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) {
        Long userId = principal.userId();
        List<Fund> funds = fundService.getAllFundsByUserId(userId);
        List<FundDto> responseDtos = funds.stream()
                .map(FundMapper::toDto)
                .toList();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FundDto> getFundById(@PathVariable @PositiveId Long id) {
        Fund fund = fundService.getFundById(id);
        FundDto responseDto = FundMapper.toDto(fund);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/{id}/add")
    public ResponseEntity<FundDto> addAmountToFund(
            @PathVariable @PositiveId Long id,
            @RequestBody @Valid AddMoneyToFundDto dto,
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) throws AccountNotFoundException {
        FundDto fund = fundService.addAmountToFund(id, dto.getAmount(), dto.getAccountId(), principal.userId());
        return new ResponseEntity<>(fund, HttpStatus.OK);
    }
    @PostMapping("/{id}/close")
    public ResponseEntity<Void> closeFund(@PathVariable @PositiveId Long id,
                                          @RequestBody CloseFundDto dto,
                                          @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) throws AccountNotFoundException {
        fundService.closeFund(id, dto.getAccountId(), principal.userId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFund(
            @PathVariable @PositiveId Long id,
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) throws FundNotFoundException {
        Long userId = principal.userId();
        fundService.deleteFund(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
