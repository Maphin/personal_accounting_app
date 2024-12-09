package com.example.personal_accounting.models;

import com.example.personal_accounting.exceptions.WithdrawalAmountInvalidException;
import com.example.personal_accounting.types.Period;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CreditAccount extends Account{
    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Period period;

//    @Override
//    public void withdraw(Double amount) {
//        if (amount <= 0) {
//            throw new WithdrawalAmountInvalidException("Withdrawal amount must be greater than 0");
//        }
//        if (this.getBalance() + this.creditLimit < amount) {
//            throw new WithdrawalAmountInvalidException("Withdrawal exceeds credit limit");
//        }
//        this.setBalance(this.getBalance() - amount);
//    }
}
