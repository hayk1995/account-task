package com.example.domain.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> clazz, Long id) {
        super(String.format("Entity %s with compositeid %d not found", clazz.getSimpleName(), id));
    }
}
