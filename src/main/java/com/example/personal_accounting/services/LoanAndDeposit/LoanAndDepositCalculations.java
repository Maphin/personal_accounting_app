package com.example.personal_accounting.services.LoanAndDeposit;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class LoanAndDepositCalculations {
    public BigDecimal calculateDeposit(BigDecimal principal, BigDecimal rate, long days) {
        BigDecimal dailyRate = rate.divide(BigDecimal.valueOf(365), 5, RoundingMode.HALF_UP);
        return principal.add(principal.multiply(dailyRate).multiply(BigDecimal.valueOf(days)));
    }
    public BigDecimal calculateLoan(BigDecimal principal, BigDecimal rate, long days) {
        BigDecimal dailyRate = rate.divide(BigDecimal.valueOf(365), 5, RoundingMode.HALF_UP);
        return principal.add(principal.multiply(dailyRate).multiply(BigDecimal.valueOf(days)));
    }
}
