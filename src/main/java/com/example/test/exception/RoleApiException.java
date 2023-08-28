package com.example.test.exception;

public abstract class RoleApiException extends RuntimeException {

    private String message;

    public RoleApiException(String message) {
        super(message);
    }
}
