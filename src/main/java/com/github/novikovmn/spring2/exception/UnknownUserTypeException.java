package com.github.novikovmn.spring2.exception;

public class UnknownUserTypeException extends RuntimeException {
    private String message;

    public UnknownUserTypeException() {
        this.message = "Неизвестный тип пользователя";
    }

    public UnknownUserTypeException(String message) {
        this.message = message;
    }
}
