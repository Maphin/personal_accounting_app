package com.example.personal_accounting.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
