package com.example.personal_accounting.services.Accounts;

import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.dto.Account.CreateAccountDto;
import com.example.personal_accounting.exceptions.AccountAlreadyExistsException;
import com.example.personal_accounting.exceptions.UserNotFoundException;
import com.example.personal_accounting.mappers.AccountMapper;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.repository.AccountRepository;
import com.example.personal_accounting.services.Accounts.State.AccountStateFactory;
import com.example.personal_accounting.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountStateFactory accountStateFactory;
    private final UserService userService;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountDto create(CreateAccountDto accountDto, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        if (accountRepository.existsByUserAndName(user, accountDto.getTitle())) {
            throw new AccountAlreadyExistsException("Account with name '" + accountDto.getTitle() + "' already exists for this user.");
        }

        Account account = new Account();
        account.setTitle(accountDto.getTitle());
        account.setBalance(accountDto.getBalance());
        account.setCurrency(accountDto.getCurrency());
        //account.setCardLimit(accountDto.getCardLimit());
        account.setUser(user);

        accountRepository.save(account);
        return AccountMapper.toDto(account);
    }

    public void withdraw(Long accountId, Double amount) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.withdraw(amount, accountStateFactory);
        accountRepository.save(account);
    }

    public void deposit(Long accountId, Double amount) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.deposit(amount, accountStateFactory);
        accountRepository.save(account);
    }

    public void closeAccount(Long accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.close(accountStateFactory);
        accountRepository.save(account);
    }
}
