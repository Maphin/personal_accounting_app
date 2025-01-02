package com.example.personal_accounting.services.ExpenseCategory;

import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.repository.ExpenseCategoryRepository;
import com.example.personal_accounting.repository.TransactionRepository;
import com.example.personal_accounting.utils.exceptions.CategoryNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final TransactionRepository transactionRepository;
    public ExpenseCategory cloneCategory(Long categoryId, String newName) {
        ExpenseCategory originalCategory = getCategoryById(categoryId);
        ExpenseCategory clonedCategory = originalCategory.clone();

        clonedCategory.setId(null);
        clonedCategory.setName(newName);

        return expenseCategoryRepository.save(clonedCategory);
    }

    @Transactional
    public ExpenseCategory create(String name) {
        ExpenseCategory category = new ExpenseCategory();
        category.setName(name);
        return expenseCategoryRepository.save(category);
    }

    public List<ExpenseCategory> getAll() {
        return expenseCategoryRepository.findAll();
    }

    public ExpenseCategory getCategoryById(Long id) {
        return expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
    public ExpenseCategory getCategoryByName(String name) {
        return expenseCategoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
    public void deleteCategory(Long id) {
        ExpenseCategory category = getCategoryById(id);

        if (transactionRepository.existsByExpenseCategory(category)) {
            throw new IllegalStateException("Cannot delete category because it is used in transactions.");
        }

        this.expenseCategoryRepository.deleteById(id);
    }
}
