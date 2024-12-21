package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.utils.exceptions.InsufficientBalanceException;
import com.example.personal_accounting.utils.exceptions.WithdrawalAmountInvalidException;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.types.AccountStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ActiveState implements AccountState {

    @Override
    public void withdraw(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new WithdrawalAmountInvalidException("Withdrawal amount must be greater than 0");
        }
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
    }

    @Override
    public void deposit(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new WithdrawalAmountInvalidException("Deposit amount must be greater than 0");
        }
        account.setBalance(account.getBalance().add(amount));
    }

    @Override
    public void close(Account account) {
        if (account.getBalance().compareTo(BigDecimal.valueOf(0)) != 0) {
            throw new IllegalStateException("Cannot close an account with a non-zero balance");
        }
        account.setState(AccountStatus.CLOSED);
    }
}