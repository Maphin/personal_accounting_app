package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.models.Account;

import java.math.BigDecimal;

public interface AccountState {
    void withdraw(Account account, BigDecimal amount);
    void deposit(Account account, BigDecimal amount);
    void close(Account account);
}
