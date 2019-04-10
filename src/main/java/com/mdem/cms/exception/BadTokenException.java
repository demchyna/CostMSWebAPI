package com.mdem.cms.exception;

public class BadTokenException extends RuntimeException {
    public BadTokenException(String message) {
        super(message);
    }
}
