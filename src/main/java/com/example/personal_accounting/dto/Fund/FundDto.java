package com.example.personal_accounting.dto.Fund;

import com.example.personal_accounting.dto.User.UserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FundDto {
    private Long id;
    private UserDto user;
    private String title;
    private BigDecimal currentAmount;
    private BigDecimal goalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
