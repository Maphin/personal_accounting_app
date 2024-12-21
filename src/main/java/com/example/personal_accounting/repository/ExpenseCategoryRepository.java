package com.example.personal_accounting.repository;

import com.example.personal_accounting.models.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
    Optional<ExpenseCategory> findById(Long id);
    Optional<ExpenseCategory> findByName(String name);
}
