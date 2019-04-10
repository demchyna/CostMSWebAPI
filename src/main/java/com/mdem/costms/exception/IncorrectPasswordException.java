package com.mdem.costms.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
