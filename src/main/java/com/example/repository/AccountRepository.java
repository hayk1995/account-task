package com.example.repository;

import com.example.domain.exception.NotFoundException;
import com.example.domain.entity.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Validated
public class AccountRepository {
    Map<Long, Account> accountMap = new ConcurrentHashMap<>();

    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accountMap.get(id));
    }

    public Account getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Account.class, id));
    }

    public Account save(@Valid Account account) {
        return accountMap.put(account.getId(), account);
    }

}
