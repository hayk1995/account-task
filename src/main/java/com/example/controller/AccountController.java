package com.example.controller;

import com.example.domain.dto.AccountDTO;
import com.example.domain.dto.AccountDepositDTO;
import com.example.domain.dto.AccountWithdrawDTO;
import com.example.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{id}/deposit")
    public AccountDTO deposit(@PathVariable Long id, @RequestBody @Valid AccountDepositDTO accountDepositDTO) {
        return accountService.deposit(id, accountDepositDTO);
    }

    @PostMapping("/{id}/withdraw")
    public AccountDTO withdraw(@PathVariable Long id, @RequestBody @Valid AccountWithdrawDTO accountWithdrawDTO) {
        return accountService.withdraw(id, accountWithdrawDTO);
    }
}
