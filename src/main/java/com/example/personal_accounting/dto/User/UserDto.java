package com.example.personal_accounting.dto.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    //private LocalDateTime createdAt;
}
