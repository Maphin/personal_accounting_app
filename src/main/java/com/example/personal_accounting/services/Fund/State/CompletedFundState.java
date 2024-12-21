package com.example.personal_accounting.services.Fund.State;

import com.example.personal_accounting.models.Fund;
import com.example.personal_accounting.services.Fund.FundService;

import java.math.BigDecimal;

public class CompletedFundState implements FundState {
    public void addAmountToFund(Fund fund, BigDecimal amount, Long accountId, Long userId, FundService fundService) {
        throw new IllegalStateException("Cannot add money to a completed fund.");
    }

    public void closeFund(Fund fund, Long accountId, Long userId, FundService fundService) {
        throw new IllegalStateException("This fund is already completed.");
    }

    public void deleteFund(Fund fund, Long userId, FundService fundService) {
        fundService.deleteFundFromRepository(fund);
    }
}
