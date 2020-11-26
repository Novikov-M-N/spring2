package com.github.novikovmn.spring2.exception;

import lombok.Getter;

@Getter
public class ManagerIsEarlierThanNeedException extends RuntimeException {
    private final String message;

    public ManagerIsEarlierThanNeedException(String message) {
        this.message = message;
    }
}
