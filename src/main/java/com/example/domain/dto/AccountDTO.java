package com.example.domain.dto;

import com.example.domain.enums.Currency;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private BigDecimal balance;
    private Currency currency;
    private String user;
}
