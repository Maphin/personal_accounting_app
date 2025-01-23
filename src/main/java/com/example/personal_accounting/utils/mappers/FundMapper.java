package com.example.personal_accounting.utils.mappers;

import com.example.personal_accounting.dto.Fund.FundDto;
import com.example.personal_accounting.dto.User.UserDto;
import com.example.personal_accounting.models.Fund;
import com.example.personal_accounting.models.User;
import org.springframework.stereotype.Component;

@Component
public class FundMapper {
    public static FundDto toDto(Fund fund) {
        if (fund == null) {
            return null;
        }

        FundDto responseDto = new FundDto();
        responseDto.setId(fund.getId());
        responseDto.setTitle(fund.getTitle());
        responseDto.setCurrentAmount(fund.getCurrentAmount());
        responseDto.setGoalAmount(fund.getGoalAmount());
        responseDto.setCreatedAt(fund.getCreatedAt());

        User user = fund.getUser();
        if (user != null) {
            UserDto userDto = UserMapper.toDto(user);
            responseDto.setUser(userDto);
        }

        return responseDto;
    }
}
