package com.example.personal_accounting.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "receipts")
@Data()
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Column(nullable = false)
    private String path;
}
