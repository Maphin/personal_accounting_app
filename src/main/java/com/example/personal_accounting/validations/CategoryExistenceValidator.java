package com.example.personal_accounting.validations;

import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import com.example.personal_accounting.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryExistenceValidator implements TransactionValidator {

    private final TransactionValidator wrappedValidator;
    private final ExpenseCategoryRepository categoryRepository;

    public CategoryExistenceValidator(TransactionValidator wrappedValidator, ExpenseCategoryRepository categoryRepository) {
        this.wrappedValidator = wrappedValidator;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void validate(CreateTransactionDto transactionDto) {
        wrappedValidator.validate(transactionDto);

        if (!categoryRepository.existsById(transactionDto.getCategoryId())) {
            throw new IllegalArgumentException("Category does not exist");
        }
    }
}

