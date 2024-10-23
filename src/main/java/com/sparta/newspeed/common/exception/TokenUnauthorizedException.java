package com.sparta.newspeed.common.exception;

    public class TokenUnauthorizedException extends RuntimeException {
        public TokenUnauthorizedException(String message) {
            super(message);
        }
}
