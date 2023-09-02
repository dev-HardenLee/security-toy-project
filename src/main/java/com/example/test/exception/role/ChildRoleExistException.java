package com.example.test.exception.role;

import com.example.test.exception.RoleApiException;

public class ChildRoleExistException extends RoleApiException {

    public ChildRoleExistException(String message) {
        super(message);
    }

}// ChildRoleExistException
