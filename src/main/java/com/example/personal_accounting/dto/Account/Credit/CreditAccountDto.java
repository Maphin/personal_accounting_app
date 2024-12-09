package com.example.personal_accounting.dto.Account.Credit;

import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.types.Period;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditAccountDto extends AccountDto {
    private Double interestRate;
    private Integer duration;
    private Period period;
}
