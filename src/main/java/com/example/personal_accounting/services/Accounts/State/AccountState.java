package com.example.personal_accounting.services.Accounts.State;

import com.example.personal_accounting.models.Account;

public interface AccountState {
    void withdraw(Account account, Double amount);
    void deposit(Account account, Double amount);
    void close(Account account);
}
