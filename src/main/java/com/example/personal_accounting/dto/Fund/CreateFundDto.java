package com.example.personal_accounting.dto.Fund;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateFundDto {
    @NotBlank(message = "Fund title is required")
    private String title;

    @NotNull(message = "Goal amount is required")
    @DecimalMin(value = "0.1", message = "Goal amount must be greater than 0")
    private BigDecimal goalAmount;
}
