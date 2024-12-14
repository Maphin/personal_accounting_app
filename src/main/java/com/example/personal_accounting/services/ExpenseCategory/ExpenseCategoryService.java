package com.example.personal_accounting.services.ExpenseCategory;

import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.repository.ExpenseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;
    public ExpenseCategory cloneCategory(Long categoryId, String newName) {
        ExpenseCategory originalCategory = expenseCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        ExpenseCategory clonedCategory = originalCategory.clone();
        clonedCategory.setId(null);
        clonedCategory.setName(newName);

        return expenseCategoryRepository.save(clonedCategory);
    }
}
