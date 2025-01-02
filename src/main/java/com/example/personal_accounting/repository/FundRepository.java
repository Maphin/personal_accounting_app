package com.example.personal_accounting.repository;

import com.example.personal_accounting.models.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FundRepository extends JpaRepository<Fund, Long> {
    List<Fund> findByUserId(Long userId);
    @Query("SELECT f FROM funds f WHERE f.user.id = :userId AND f.createdAt BETWEEN :startDate AND :endDate")
    List<Fund> findFundsByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
