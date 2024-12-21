package com.example.personal_accounting.controllers;

import com.example.personal_accounting.dto.ExpenseCategory.CreateExpenseCategoryDto;
import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.services.ExpenseCategory.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;
    @PostMapping("/admin/clone")
    public ResponseEntity<ExpenseCategory> cloneCategory(@RequestParam Long categoryId, @RequestParam String newName) {
        ExpenseCategory clonedCategory = expenseCategoryService.cloneCategory(categoryId, newName);
        return ResponseEntity.status(HttpStatus.CREATED).body(clonedCategory);
    }

    @PostMapping
    public ResponseEntity<ExpenseCategory> createCategory(@RequestBody CreateExpenseCategoryDto dto) {
        ExpenseCategory createdCategory = expenseCategoryService.create(dto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategory>> getAllCategories() {
        List<ExpenseCategory> categories = expenseCategoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
