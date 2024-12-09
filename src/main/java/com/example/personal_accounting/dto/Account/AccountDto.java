package com.example.personal_accounting.dto.Account;

import com.example.personal_accounting.dto.User.UserDto;
import com.example.personal_accounting.types.Currency;
import lombok.Data;

import java.time.Instant;

@Data
public class AccountDto {
    private Long id;
    private String title;
    private Double balance;
    private Currency currency;
    private Instant createdAt;
    private UserDto user;
}
