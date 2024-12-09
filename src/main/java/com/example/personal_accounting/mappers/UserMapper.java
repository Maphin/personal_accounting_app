package com.example.personal_accounting.mappers;

import com.example.personal_accounting.dto.User.UserDto;
import com.example.personal_accounting.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
