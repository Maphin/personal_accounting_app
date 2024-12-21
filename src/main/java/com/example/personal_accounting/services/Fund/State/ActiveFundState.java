package com.example.personal_accounting.services.Fund.State;

import com.example.personal_accounting.models.Fund;
import com.example.personal_accounting.services.Fund.FundService;
import com.example.personal_accounting.types.FundStatus;
import com.example.personal_accounting.types.TransactionType;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

public class ActiveFundState implements FundState {
    public void addAmountToFund(Fund fund, BigDecimal amount, Long accountId, Long userId, FundService fundService) throws AccountNotFoundException {
        fundService.makeTransaction(accountId, amount, userId, fund, "Fund", TransactionType.WITHDRAW, "Transfer to fund: ");
        fundService.topUpFund(fund, amount);
    }

    public void closeFund(Fund fund, Long accountId, Long userId, FundService fundService) throws AccountNotFoundException {
        BigDecimal accumulatedAmount = fund.getCurrentAmount();

        if (accumulatedAmount.compareTo(BigDecimal.ZERO) > 0) {
            fundService.makeTransaction(accountId, accumulatedAmount, userId, fund, "Fund Closure", TransactionType.DEPOSIT, "Transfer from fund: ");
            fund.setCurrentAmount(BigDecimal.ZERO);
        }

        fund.setStatus(FundStatus.COMPLETED);
        fundService.saveFund(fund);
    }
    public void deleteFund(Fund fund, Long userId, FundService fundService) {
        fundService.deleteFundFromRepository(fund);
    }
}
