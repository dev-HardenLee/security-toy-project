package com.example.test.exception.role;

import com.example.test.exception.RoleApiException;

public class RoleAlreadyExistException extends RoleApiException {

    public RoleAlreadyExistException(String message) {
        super(message);
    }
}
