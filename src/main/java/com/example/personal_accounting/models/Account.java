package com.example.personal_accounting.models;

import com.example.personal_accounting.exceptions.InsufficientBalanceException;
import com.example.personal_accounting.exceptions.WithdrawalAmountInvalidException;
import com.example.personal_accounting.services.Accounts.State.AccountState;
import com.example.personal_accounting.services.Accounts.State.AccountStateFactory;
import com.example.personal_accounting.types.AccountStatus;
import com.example.personal_accounting.types.AccountType;
import com.example.personal_accounting.types.Currency;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@Data()
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType; // CREDIT, DEBIT, FUND

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus state = AccountStatus.ACTIVE; // ACTIVE, SUSPENDED, CLOSED

//    @Column
//    private Double cardLimit;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public void withdraw(Double amount, AccountStateFactory stateFactory) {
        AccountState stateHandler = stateFactory.getState(this.state);
        stateHandler.withdraw(this, amount);
    }

    public void deposit(Double amount, AccountStateFactory stateFactory) {
        AccountState stateHandler = stateFactory.getState(this.state);
        stateHandler.deposit(this, amount);
    }

    public void close(AccountStateFactory stateFactory) {
        AccountState stateHandler = stateFactory.getState(this.state);
        stateHandler.close(this);
    }
}
