package com.example.personal_accounting.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FundNotFoundException extends RuntimeException {
    public FundNotFoundException(String message) {
        super(message);
    }
}
