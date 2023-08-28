package com.example.test.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenNotFoundException extends AuthenticationException{

	public JwtTokenNotFoundException(String msg) {
		super(msg);
	}// constructor
	
}// JwtTokenNotFoundException
