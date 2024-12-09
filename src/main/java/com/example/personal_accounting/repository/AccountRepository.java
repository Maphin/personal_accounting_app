package com.example.personal_accounting.repository;

import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    boolean existsByUserAndName(User user, String name);
}
