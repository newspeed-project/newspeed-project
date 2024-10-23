package com.sparta.newspeed.common;

import lombok.Getter;

@Getter
public class ResponseWrapper<T> {
    private final int statusCode;
    private final String message;
    private final T data;

    public ResponseWrapper(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
