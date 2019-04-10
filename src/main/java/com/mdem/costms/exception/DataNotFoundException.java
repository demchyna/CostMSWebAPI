package com.mdem.costms.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}