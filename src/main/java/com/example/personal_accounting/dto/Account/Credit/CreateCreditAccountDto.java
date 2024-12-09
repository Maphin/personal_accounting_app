package com.example.personal_accounting.dto.Account.Credit;

import com.example.personal_accounting.dto.Account.CreateAccountDto;
import com.example.personal_accounting.types.Period;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateCreditAccountDto extends CreateAccountDto {
    private Double interestRate;
    private int duration;
    private Period period;
}
