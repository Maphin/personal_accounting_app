package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.models.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SuspendedState implements AccountState {

    @Override
    public void withdraw(Account account, BigDecimal amount) {
        throw new IllegalStateException("Withdrawals are not allowed for suspended accounts.");
    }

    @Override
    public void deposit(Account account, BigDecimal amount) {
        throw new IllegalStateException("Deposits are not allowed for suspended accounts.");
    }

    @Override
    public void close(Account account) {
        throw new IllegalStateException("Cannot close a suspended account.");
    }
}

