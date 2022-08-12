package com.example.domain.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Account has insufficient funds");
    }

}
