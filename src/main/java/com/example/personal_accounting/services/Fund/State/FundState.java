package com.example.personal_accounting.services.Fund.State;

import com.example.personal_accounting.models.Fund;
import com.example.personal_accounting.services.Fund.FundService;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

public interface FundState {
    void addAmountToFund(Fund fund, BigDecimal amount, Long accountId, Long userId, FundService fundTransactionService) throws AccountNotFoundException;
    void closeFund(Fund fund, Long accountId, Long userId, FundService fundService) throws AccountNotFoundException;
    void deleteFund(Fund fund, Long userId, FundService fundService);
}
