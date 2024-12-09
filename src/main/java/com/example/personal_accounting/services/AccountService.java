package com.example.personal_accounting.services;

import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.dto.Account.CreateAccountDto;
import com.example.personal_accounting.exceptions.AccountAlreadyExistsException;
import com.example.personal_accounting.exceptions.UserNotFoundException;
import com.example.personal_accounting.mappers.AccountMapper;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountDto create(CreateAccountDto accountDto, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (accountRepository.existsByUserAndName(user, accountDto.getName())) {
            throw new AccountAlreadyExistsException("Account with name '" + accountDto.getName() + "' already exists for this user.");
        }

        Account account = new Account();
        account.setName(accountDto.getName());
        account.setBalance(accountDto.getBalance());
        account.setCurrency(accountDto.getCurrency());
        account.setCardLimit(accountDto.getCardLimit());
        account.setUser(user); // Set the user

        // Save the account entity
        accountRepository.save(account);

        // Map the entity to a DTO for response
        return AccountMapper.toDto(account);
    }
}
