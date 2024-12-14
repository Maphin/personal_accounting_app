package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.models.Account;
import org.springframework.stereotype.Component;

@Component
public class ClosedState implements AccountState {

    @Override
    public void withdraw(Account account, Double amount) {
        throw new IllegalStateException("Account is closed. No transactions are allowed.");
    }

    @Override
    public void deposit(Account account, Double amount) {
        throw new IllegalStateException("Account is closed. No transactions are allowed.");
    }

    @Override
    public void close(Account account) {
        throw new IllegalStateException("Account is already closed.");
    }
}

