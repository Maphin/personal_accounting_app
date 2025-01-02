package com.example.personal_accounting.models.Flyweight;

import com.example.personal_accounting.types.Type;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LoanAndDepositFlyweightFactory {
    private static final Map<String, LoanAndDepositFlyweight> flyweights = new HashMap<>();

    public static LoanAndDepositFlyweight getFlyweight(Type type, BigDecimal principalAmount, BigDecimal interestRate) {
        String key = type + "-" + principalAmount + "-" + interestRate;

        if (!flyweights.containsKey(key)) {
            LoanAndDepositFlyweight flyweight = new LoanAndDepositFlyweight(type, principalAmount, interestRate);
            flyweights.put(key, flyweight);
        }

        return flyweights.get(key);
    }
}
