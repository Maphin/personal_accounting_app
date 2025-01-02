package com.example.personal_accounting.services.Statistics.Transaction.Bridge;

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
    public BigDecimal calculateTotalByTypeAndAccount(TransactionType type, LocalDate startDate, LocalDate endDate, Long accountId) {
        return transactionRepository.calculateTotalAmountByTypeAndDateRangeAndAccount(type, startDate, endDate, accountId);
    }
}
