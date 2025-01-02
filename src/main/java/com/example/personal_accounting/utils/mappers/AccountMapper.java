package com.example.personal_accounting.utils.mappers;

import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.dto.User.UserDto;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public static AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setTitle(account.getTitle());
        accountDto.setBalance(account.getBalance());
        accountDto.setCreatedAt(account.getCreatedAt());

        // Map User entity to UserDto
        if (account.getUser() != null) {
            User user = account.getUser();
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            accountDto.setUser(userDto);
        }

        return accountDto;
    }
    public static Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        Account account = new Account();
        account.setId(accountDto.getId());
        account.setTitle(accountDto.getTitle());
        account.setBalance(accountDto.getBalance());
        account.setCreatedAt(accountDto.getCreatedAt());

        // You can set the User entity manually if needed
        // For example, setting the User from the principal or some other logic
        // account.setUser(userEntity);

        return account;
    }
}
