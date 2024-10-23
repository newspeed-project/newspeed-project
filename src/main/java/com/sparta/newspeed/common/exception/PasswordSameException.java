package com.sparta.newspeed.common.exception;

public class PasswordSameException extends RuntimeException {
    public PasswordSameException(String message) {
        super(message);
    }
}
