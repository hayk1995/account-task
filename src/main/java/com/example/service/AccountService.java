package com.example.service;


import com.example.concurent.PreventDuplicate;
import com.example.domain.dto.AccountDTO;
import com.example.domain.dto.AccountDepositDTO;
import com.example.domain.dto.AccountWithdrawDTO;
import com.example.domain.exception.InsufficientFundsException;
import com.example.domain.entity.Account;
import com.example.mapper.AccountMapper;
import com.example.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @PreventDuplicate("'account_balance_' + #id")
    public AccountDTO deposit(Long id, AccountDepositDTO accountDepositDTO) {
        log.info("Depositing to account {} an amount {}", id , accountDepositDTO.getAmount());

        Account account = accountRepository.getById(id);
        BigDecimal newBalance = account.getBalance().add(accountDepositDTO.getAmount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        log.info("Deposited to account {} an amount {}", id , accountDepositDTO.getAmount());
        return accountMapper.toDTO(account);
    }

    @PreventDuplicate("'account_balance_' + #id")
    public AccountDTO withdraw(Long id, AccountWithdrawDTO accountWithdrawDTO) {
        log.info("Withdrawing from account {} an amount {}", id , accountWithdrawDTO.getAmount());

        Account account = accountRepository.getById(id);
        if (account.getBalance().compareTo(accountWithdrawDTO.getAmount()) < 0) {
            log.info("Failed to withdraw from account {} an amount {}", id, accountWithdrawDTO.getAmount());
            throw new InsufficientFundsException();
        }
        BigDecimal newBalance = account.getBalance().subtract(accountWithdrawDTO.getAmount());
        account.setBalance(newBalance);
        accountRepository.save(account);

        log.info("Withdrew from account {} an amount {}", id , accountWithdrawDTO.getAmount());
        return accountMapper.toDTO(account);
    }

}
