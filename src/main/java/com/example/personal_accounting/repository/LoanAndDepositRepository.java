package com.example.personal_accounting.repository;

import com.example.personal_accounting.models.LoanAndDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanAndDepositRepository extends JpaRepository<LoanAndDeposit, Long> {
    List<LoanAndDeposit> findAllByUserId(Long userId);
}
