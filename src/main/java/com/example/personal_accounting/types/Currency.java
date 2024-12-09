package com.example.personal_accounting.types;

public enum Currency {
    USD("United States Dollar"),
    EUR("Euro"),
    UAH("Ukrainian Hryvnia");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
