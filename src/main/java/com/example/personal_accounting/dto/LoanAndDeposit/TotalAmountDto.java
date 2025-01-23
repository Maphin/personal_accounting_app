package com.example.personal_accounting.dto.LoanAndDeposit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalAmountDto {
    private BigDecimal totalAmount;
}
