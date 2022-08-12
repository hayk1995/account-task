package com.example;

import com.example.domain.enums.Currency;
import com.example.domain.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner start(AccountRepository accountRepository) {
        return args -> {
            Account johnUsd = new Account(1L, BigDecimal.ZERO, Currency.USD, "John");
            Account johnEur = new Account(2L, BigDecimal.ZERO, Currency.EUR, "John");
            Account markUsd = new Account(3L, BigDecimal.ZERO, Currency.USD, "Mark");
            accountRepository.save(johnUsd);
            accountRepository.save(johnEur);
            accountRepository.save(markUsd);
        };
    }
}
