package com.example.domain.entity;


import com.example.domain.enums.Currency;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Long id;

    @DecimalMin("0.0")
    private BigDecimal balance;

    @NotNull
    private Currency currency = Currency.USD;

    @NotEmpty
    private String user;
}

