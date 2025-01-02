package com.example.personal_accounting.repository;

import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.models.Transaction;
import com.example.personal_accounting.types.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByExpenseCategoryId(Long categoryId);
    List<Transaction> findByIsPeriodic(boolean isPeriodic);
    boolean existsByExpenseCategory(ExpenseCategory expenseCategory);

    @Query("SELECT t FROM transactions t " +
            "JOIN t.account a " +
            "WHERE a.user.id = :userId")
    List<Transaction> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM transactions t " +
            "WHERE t.transactionType = :type " +
            "AND t.transactionDate BETWEEN :startDate AND :endDate " +
            "AND t.account.id = :accountId")
    BigDecimal calculateTotalAmountByTypeAndDateRangeAndAccount(
            @Param("type") TransactionType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("accountId") Long accountId
    );
}
