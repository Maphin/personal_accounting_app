package com.example.personal_accounting.repository;

import com.example.personal_accounting.models.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {
    List<Fund> findByUserId(Long userId);
}
