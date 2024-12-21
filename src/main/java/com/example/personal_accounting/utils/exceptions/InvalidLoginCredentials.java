package com.example.personal_accounting.utils.exceptions;

public class InvalidLoginCredentials extends RuntimeException{
    public InvalidLoginCredentials(String message) {
        super(message);
    }
}
