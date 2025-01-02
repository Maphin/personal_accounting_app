package com.example.personal_accounting.models;

import com.example.personal_accounting.services.Accounts.State.AccountState;
import com.example.personal_accounting.services.Accounts.State.AccountStateFactory;
import com.example.personal_accounting.types.AccountStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@Data()
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.valueOf(0.0);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus state = AccountStatus.ACTIVE; // ACTIVE, SUSPENDED, CLOSED

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column()
    private LocalDateTime updatedAt;
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void withdraw(BigDecimal amount, AccountStateFactory stateFactory) {
        AccountState stateHandler = stateFactory.getState(this.state);
        stateHandler.withdraw(this, amount);
    }

    public void deposit(BigDecimal amount, AccountStateFactory stateFactory) {
        AccountState stateHandler = stateFactory.getState(this.state);
        stateHandler.deposit(this, amount);
    }

    public void close(AccountStateFactory stateFactory) {
        AccountState stateHandler = stateFactory.getState(this.state);
        stateHandler.close(this);
    }
}
