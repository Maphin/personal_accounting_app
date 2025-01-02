package com.example.personal_accounting.services.Statistics.Bridge;

import com.example.personal_accounting.repository.TransactionRepository;
import com.example.personal_accounting.types.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class DatabaseTransactionStatisticsImplementation implements TransactionStatisticsImplementation {
    private final TransactionRepository transactionRepository;
    public DatabaseTransactionStatisticsImplementation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    @Override
    public BigDecimal calculateTotalByType(TransactionType type, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.calculateTotalAmountByTypeAndDateRange(type, startDate, endDate);
    }
}
