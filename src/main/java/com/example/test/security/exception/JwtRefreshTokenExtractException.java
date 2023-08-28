package com.example.test.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtRefreshTokenExtractException extends AuthenticationException {

	public JwtRefreshTokenExtractException(String msg) {
		super(msg);
	}
	
	
	
}
