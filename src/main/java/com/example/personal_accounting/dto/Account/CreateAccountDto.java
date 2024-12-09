package com.example.personal_accounting.dto.Account;

//import com.example.personal_accounting.models.FinancialPlan;
import com.example.personal_accounting.types.AccountType;
import com.example.personal_accounting.types.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CreateAccountDto {
    @NotBlank(message = "Account title is required")
    private String title;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance must be zero or positive")
    private Double balance;

    @NotNull(message = "Currency is required")
    private Currency currency;

//    @PositiveOrZero(message = "Card limit must be zero or positive")
//    private Double cardLimit;

    //private FinancialPlan financialId;
}
