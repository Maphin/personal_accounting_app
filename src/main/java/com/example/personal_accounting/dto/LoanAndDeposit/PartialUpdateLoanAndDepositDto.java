package com.example.personal_accounting.dto.LoanAndDeposit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Data
public class PartialUpdateLoanAndDepositDto {
    private Optional<String> title = Optional.empty();
    private Optional<BigDecimal> principalAmount = Optional.empty();
    private Optional<BigDecimal> interestRate = Optional.empty();
    private Optional<LocalDate> startDate = Optional.empty();
    private Optional<LocalDate> endDate = Optional.empty();
}
