package com.example.personal_accounting.exceptions;

public class InvalidLoginCredentials extends RuntimeException{
    public InvalidLoginCredentials(String message) {
        super(message);
    }
}
