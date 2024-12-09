package com.example.personal_accounting.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class FundAccount extends Account {
    @Column(nullable = false)
    private Double goalAmount;
}
