package com.example.personal_accounting.controllers;

import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.services.ExpenseCategory.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;
    @PostMapping("/clone")
    public ResponseEntity<ExpenseCategory> cloneCategory(@RequestParam Long categoryId, @RequestParam String newName) {
        ExpenseCategory clonedCategory = expenseCategoryService.cloneCategory(categoryId, newName);
        return ResponseEntity.status(HttpStatus.CREATED).body(clonedCategory);
    }
}
