package com.example.personal_accounting.utils.mappers;

import com.example.personal_accounting.dto.LoanAndDeposit.LoanAndDepositDto;
import com.example.personal_accounting.models.LoanAndDeposit;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanAndDepositMapper {
    private final ModelMapper modelMapper;

    public LoanAndDeposit toEntity(LoanAndDepositDto dto) {
        return modelMapper.map(dto, LoanAndDeposit.class);
    }

    public LoanAndDepositDto toDto(LoanAndDeposit loanAndDeposit) {
        return modelMapper.map(loanAndDeposit, LoanAndDepositDto.class);
    }
}
