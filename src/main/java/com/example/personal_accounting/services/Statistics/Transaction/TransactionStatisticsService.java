package com.example.personal_accounting.services.Statistics.Transaction;

import com.example.personal_accounting.services.Statistics.Transaction.Bridge.DatabaseTransactionStatisticsImplementation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionStatisticsService {

    private final MonthlyTransactionStatistics statistics;

    public TransactionStatisticsService(DatabaseTransactionStatisticsImplementation implementation) {
        this.statistics = new MonthlyTransactionStatistics(implementation);
    }

    public BigDecimal getTotalIncome(LocalDate startDate, LocalDate endDate, Long accountId) {
        return statistics.calculateIncome(startDate, endDate, accountId);
    }

    public BigDecimal getTotalExpenses(LocalDate startDate, LocalDate endDate, Long accountId) {
        return statistics.calculateExpenses(startDate, endDate, accountId);
    }
}
