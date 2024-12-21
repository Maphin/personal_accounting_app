package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.models.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ClosedState implements AccountState {
    public void withdraw(Account account, BigDecimal amount) {
        throw new IllegalStateException("Account is closed. No transactions are allowed.");
    }

    public void deposit(Account account, BigDecimal amount) {
        throw new IllegalStateException("Account is closed. No transactions are allowed.");
    }
    public void close(Account account) {
        throw new IllegalStateException("Account is already closed.");
    }
}

