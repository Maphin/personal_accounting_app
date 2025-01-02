package com.example.personal_accounting.services.Statistics.Transaction;

import com.example.personal_accounting.services.Statistics.Transaction.Bridge.TransactionStatistics;
import com.example.personal_accounting.services.Statistics.Transaction.Bridge.TransactionStatisticsImplementation;
import com.example.personal_accounting.types.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MonthlyTransactionStatistics extends TransactionStatistics {

    public MonthlyTransactionStatistics(TransactionStatisticsImplementation implementation) {
        super(implementation);
    }

    @Override
    public BigDecimal calculateIncome(LocalDate startDate, LocalDate endDate, Long accountId) {
        return implementation.calculateTotalByTypeAndAccount(TransactionType.DEPOSIT, startDate, endDate, accountId);
    }

    @Override
    public BigDecimal calculateExpenses(LocalDate startDate, LocalDate endDate, Long accountId) {
        return implementation.calculateTotalByTypeAndAccount(TransactionType.WITHDRAW, startDate, endDate, accountId);
    }
}
