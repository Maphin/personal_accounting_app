package com.example.personal_accounting;

import com.example.personal_accounting.dto.Account.AccountDto;
import com.example.personal_accounting.utils.mappers.AccountMapper;
import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.types.Currency;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
public class AccountMapperTest {
    private final AccountMapper accountMapper;

    @Test
    void testToDto() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        Account account = new Account();
        account.setId(101L);
        account.setTitle("Savings");
        account.setBalance(BigDecimal.valueOf(1000.0));
        account.setCurrency(Currency.USD);
        account.setUser(user);

        AccountDto dto = AccountMapper.toDto(account);

        assertEquals(account.getId(), dto.getId());
        assertEquals(account.getTitle(), dto.getTitle());
        assertEquals(account.getBalance(), dto.getBalance());
        assertEquals(account.getCurrency(), dto.getCurrency());
        assertEquals(account.getUser().getId(), dto.getUser().getId());
        assertEquals(account.getUser().getUsername(), dto.getUser().getUsername());
    }
}
