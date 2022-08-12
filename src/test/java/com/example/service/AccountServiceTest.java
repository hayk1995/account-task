package com.example.service;


import com.example.domain.dto.AccountDTO;
import com.example.domain.dto.AccountDepositDTO;
import com.example.domain.dto.AccountWithdrawDTO;
import com.example.domain.enums.Currency;
import com.example.domain.exception.InsufficientFundsException;
import com.example.domain.entity.Account;
import com.example.mapper.AccountMapper;
import com.example.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    void initService() {
        accountService = new AccountService(accountRepository, new AccountMapper());
    }

    @Test
    void shouldAddDepositToAccount() {
        when(accountRepository.getById(1L)).thenReturn(new Account(1L, BigDecimal.valueOf(0), Currency.USD, "John"));
        when(accountRepository.save(Mockito.any(Account.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        AccountDTO accountDTO = accountService.deposit(1L, new AccountDepositDTO(BigDecimal.TEN));
        assertThat(accountDTO.getBalance()).isEqualTo(BigDecimal.valueOf(10));
    }

    @Test
    void shouldWithDrawFromAccount() {
        when(accountRepository.getById(1L)).thenReturn(new Account(1L, BigDecimal.valueOf(20), Currency.USD, "John"));
        when(accountRepository.save(Mockito.any(Account.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        AccountDTO accountDTO = accountService.withdraw(1L, new AccountWithdrawDTO(BigDecimal.valueOf(10)));
        assertThat(accountDTO.getBalance()).isEqualTo(BigDecimal.valueOf(10));
    }

    @Test
    void shouldFailWithDrawWhenInsufficientBalance() {
        when(accountRepository.getById(1L)).thenReturn(new Account(1L, BigDecimal.valueOf(10), Currency.USD, "John"));

        assertThrows(InsufficientFundsException.class, () -> accountService.withdraw(1L, new AccountWithdrawDTO(BigDecimal.valueOf(20))));
    }

}
