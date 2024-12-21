package com.example.personal_accounting.services.Accounts;

import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.dto.Account.CreateAccountDto;
import com.example.personal_accounting.types.Currency;
import com.example.personal_accounting.utils.exceptions.AccountAlreadyExistsException;
import com.example.personal_accounting.utils.exceptions.UserNotFoundException;
import com.example.personal_accounting.utils.mappers.AccountMapper;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.repository.AccountRepository;
import com.example.personal_accounting.services.Accounts.State.AccountStateFactory;
import com.example.personal_accounting.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountStateFactory accountStateFactory;
    private final UserService userService;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountDto create(CreateAccountDto accountDto, Long userId) {
        User user = userService.findById(userId);

        if (accountRepository.existsByUserAndTitle(user, accountDto.getTitle())) {
            throw new AccountAlreadyExistsException("Account with name '" + accountDto.getTitle() + "' already exists for this user.");
        }

        Account account = new Account();
        account.setTitle(accountDto.getTitle());
        account.setBalance(accountDto.getBalance());
        account.setCurrency(Currency.valueOf(accountDto.getCurrency()));
        account.setUser(user);

        save(account);
        return AccountMapper.toDto(account);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public List<Account> getUserAccounts(Long userId) {
        return accountRepository.findAllByUserId(userId);
    }

    public Account getById(Long accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public void save(Account account) {
        accountRepository.save(account);
    }
    @Transactional
    public void delete(Long accountId) throws AccountNotFoundException {
        Account account = getById(accountId);
        accountRepository.delete(account);
    }

    public void withdraw(Long accountId, BigDecimal amount) throws AccountNotFoundException {
        Account account = getById(accountId);
        account.withdraw(amount, accountStateFactory);
        save(account);
    }

    public void deposit(Long accountId, BigDecimal amount) throws AccountNotFoundException {
        Account account = getById(accountId);
        account.deposit(amount, accountStateFactory);
        save(account);
    }

    public void closeAccount(Long accountId) throws AccountNotFoundException {
        Account account = getById(accountId);
        account.close(accountStateFactory);
        save(account);
    }
}
