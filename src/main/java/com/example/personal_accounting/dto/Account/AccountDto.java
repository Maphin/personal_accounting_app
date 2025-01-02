package com.example.personal_accounting.dto.Account;

import com.example.personal_accounting.dto.User.UserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDto {
    private Long id;
    private String title;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
}
