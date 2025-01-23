package com.example.personal_accounting.dto.Fund;

import com.example.personal_accounting.types.FundStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateFundDto {
    private String title;
    private BigDecimal goalAmount;
}
