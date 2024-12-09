package com.example.personal_accounting.models;

import com.example.personal_accounting.exceptions.InsufficientBalanceException;
import com.example.personal_accounting.exceptions.WithdrawalAmountInvalidException;
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

//    @Column
//    private Double cardLimit;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public void withdraw(Double amount) {
        if (amount <= 0) {
            throw new WithdrawalAmountInvalidException("Withdrawal amount must be greater than 0");
        }
        if (this.balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        this.balance -= amount;
    }

    public void topUp(Double amount) {
        if (amount <= 0) {
            throw new WithdrawalAmountInvalidException("Withdrawal amount must be greater than 0");
        }
        this.balance += amount;
    }
}
