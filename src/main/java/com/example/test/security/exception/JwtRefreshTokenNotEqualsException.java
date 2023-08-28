package com.example.test.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtRefreshTokenNotEqualsException extends AuthenticationException {

	public JwtRefreshTokenNotEqualsException(String msg) {
		super(msg);
	}
	
	
	
}// JwtRefreshTokenNotEqualsException
