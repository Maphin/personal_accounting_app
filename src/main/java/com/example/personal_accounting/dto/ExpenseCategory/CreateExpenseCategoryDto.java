package com.example.personal_accounting.dto.ExpenseCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateExpenseCategoryDto {
    @NotBlank(message = "Expense category name is required")
    private String name;
}
