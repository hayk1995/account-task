package com.example.mapper;

import com.example.domain.dto.AccountDTO;
import com.example.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDTO toDTO(Account account) {
        return new AccountDTO(account.getBalance(), account.getCurrency(), account.getUser());
    }
}
