package com.example.personal_accounting.services.Statistics.Bridge;

import com.example.personal_accounting.types.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionStatisticsImplementation {
    BigDecimal calculateTotalByType(TransactionType type, LocalDate startDate, LocalDate endDate);
}
