package com.example.personal_accounting.dto.Account.Fund;

import com.example.personal_accounting.dto.Account.CreateAccountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateFundAccountDto extends CreateAccountDto {
    private Double goalAmount;
}
