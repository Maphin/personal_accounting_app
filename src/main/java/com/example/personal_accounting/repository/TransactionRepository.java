package com.example.personal_accounting.repository;

import com.example.personal_accounting.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByExpenseCategoryId(Long categoryId);
    List<Transaction> findByIsPeriodic(boolean isPeriodic);

    @Query("SELECT t FROM transactions t " +
            "JOIN t.account a " +
            "WHERE a.user.id = :userId")
    List<Transaction> findAllByUserId(@Param("userId") Long userId);
}
