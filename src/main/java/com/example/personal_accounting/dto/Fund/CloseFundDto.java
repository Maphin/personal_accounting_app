package com.example.personal_accounting.dto.Fund;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CloseFundDto {
    @NotNull(message = "Account ID must not be null")
    @Positive(message = "Account ID must be positive")
    private Long accountId;
}
