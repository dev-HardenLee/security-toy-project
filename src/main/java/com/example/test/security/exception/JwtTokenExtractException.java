package com.example.test.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExtractException extends AuthenticationException {

	public JwtTokenExtractException(String msg) {
		super(msg);
	}
	
	
	
}
