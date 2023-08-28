package com.example.test.security.exception;

import org.springframework.security.authentication.InsufficientAuthenticationException;

public class AdminKeyNotExistException extends InsufficientAuthenticationException {

    public AdminKeyNotExistException(String msg) {
        super(msg);
    }
}
