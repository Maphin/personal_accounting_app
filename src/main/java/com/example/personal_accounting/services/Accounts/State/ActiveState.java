package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.exceptions.InsufficientBalanceException;
import com.example.personal_accounting.exceptions.WithdrawalAmountInvalidException;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.types.AccountStatus;
import org.springframework.stereotype.Component;

@Component
public class ActiveState implements AccountState {

    @Override
    public void withdraw(Account account, Double amount) {
        if (amount <= 0) {
            throw new WithdrawalAmountInvalidException("Withdrawal amount must be greater than 0");
        }
        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
    }

    @Override
    public void deposit(Account account, Double amount) {
        if (amount <= 0) {
            throw new WithdrawalAmountInvalidException("Deposit amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void close(Account account) {
        if (account.getBalance() != 0) {
            throw new IllegalStateException("Cannot close an account with a non-zero balance");
        }
        account.setState(AccountStatus.CLOSED);
    }
}