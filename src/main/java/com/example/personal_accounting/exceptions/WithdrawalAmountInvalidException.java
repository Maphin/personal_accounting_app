package com.example.personal_accounting.exceptions;

public class WithdrawalAmountInvalidException extends RuntimeException{
    public WithdrawalAmountInvalidException(String message) {
        super(message);
    }
}
