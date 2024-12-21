package com.example.personal_accounting.dto.Transaction;

import com.example.personal_accounting.models.Account;
import lombok.Data;

@Data
public class TransactionDto {
    private Long id;
    private Account account;
}
