package com.example.personal_accounting.dto.Transaction;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTransactionDto {
    private Long accountId;
    private Long categoryId;
    private Double amount;
    private String description;
    private LocalDate date;
}
