package com.example.personal_accounting.models;

import com.example.personal_accounting.types.Type;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "loans_and_deposits")
@Data
public class LoanAndDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false, precision = 15, scale = 2, columnDefinition = "DECIMAL(15,2)")
    private BigDecimal principalAmount;

    @Column(nullable = false, precision = 15, scale = 2, columnDefinition = "DECIMAL(5,2)")
    private BigDecimal interestRate;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;
}
