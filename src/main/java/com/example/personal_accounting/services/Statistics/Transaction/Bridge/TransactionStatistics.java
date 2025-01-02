package com.example.personal_accounting.services.Statistics.Transaction.Bridge;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class TransactionStatistics {
    protected TransactionStatisticsImplementation implementation;

    public TransactionStatistics(TransactionStatisticsImplementation implementation) {
        this.implementation = implementation;
    }

    public abstract BigDecimal calculateIncome(LocalDate startDate, LocalDate endDate, Long accountId);

    public abstract BigDecimal calculateExpenses(LocalDate startDate, LocalDate endDate, Long accountId);
}
