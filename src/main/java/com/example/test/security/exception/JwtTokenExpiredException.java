package com.example.test.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException{

	public JwtTokenExpiredException(String msg) {
		super(msg);
	}
	
	
	
}
