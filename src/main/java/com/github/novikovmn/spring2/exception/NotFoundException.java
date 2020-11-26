package com.github.novikovmn.spring2.exception;

import lombok.Getter;

@Getter
public abstract class NotFoundException extends RuntimeException {
    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
