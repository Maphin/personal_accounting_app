package com.example.personal_accounting.dto.LoanAndDeposit;

import com.example.personal_accounting.types.Type;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanAndDepositDto {
    private Long id;
    private String title;
    private Type type;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
}
