package com.example.personal_accounting.utils.exceptions;

public class WithdrawalAmountInvalidException extends RuntimeException {
    public WithdrawalAmountInvalidException(String message) {
        super(message);
    }
}
