package com.example.domain.enums;


public enum Currency {
    USD("usd"), EUR("eur");

    public final String code;

    Currency(String code) {
        this.code = code;
    }
}
