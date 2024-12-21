//package com.example.personal_accounting.validations;
//
//import com.example.personal_accounting.repository.AccountRepository;
//import com.example.personal_accounting.repository.ExpenseCategoryRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ValidatorConfig {
//
//    @Bean
//    public TransactionValidator transactionValidator(BasicTransactionValidator basicValidator,
//                                                     AccountRepository accountRepository,
//                                                     ExpenseCategoryRepository categoryRepository
//                                                     ) {
//        return new CategoryExistenceValidator(
//                new AccountExistenceValidator(basicValidator, accountRepository),
//                categoryRepository
//        );
//    }
//}
