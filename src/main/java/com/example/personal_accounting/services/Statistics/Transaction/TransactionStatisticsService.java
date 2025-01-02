package com.example.personal_accounting.services.Statistics;

import org.springframework.stereotype.Service;

@Service
public class TransactionStatisticsService {

    private final MonthlyTransactionStatistics statistics;

    public TransactionStatisticsService(DatabaseTransactionStatisticsImplementation implementation) {
        this.statistics = new MonthlyTransactionStatistics(implementation);
    }

    public BigDecimal getTotalIncome(LocalDate startDate, LocalDate endDate) {
        return statistics.calculateIncome(startDate, endDate);
    }

    public BigDecimal getTotalExpenses(LocalDate startDate, LocalDate endDate) {
        return statistics.calculateExpenses(startDate, endDate);
    }
}
