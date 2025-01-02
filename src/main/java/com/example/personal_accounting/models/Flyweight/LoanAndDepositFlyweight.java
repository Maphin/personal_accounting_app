package com.example.personal_accounting.models.Flyweight;

import com.example.personal_accounting.types.Type;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Data
@RequiredArgsConstructor
public class LoanAndDepositFlyweight {
    private final Type type;
    private final BigDecimal principalAmount;
    private final BigDecimal interestRate;
}
