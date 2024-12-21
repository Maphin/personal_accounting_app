package com.example.personal_accounting.utils;

import com.example.personal_accounting.utils.exceptions.ForbiddenException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CheckPermissions {
    public static boolean checkPermissions(Long id, Long userId, String message) {
        if (!Objects.equals(id, userId)) {
            throw new ForbiddenException(message);
        }
        return true;
    }
}
