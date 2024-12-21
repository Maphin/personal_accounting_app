package com.example.personal_accounting.utils.mappers;

import com.example.personal_accounting.dto.User.UserDto;
import com.example.personal_accounting.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        //userDto.setCreatedAt(user.getCreatedAt());

        return userDto;
    }
}
