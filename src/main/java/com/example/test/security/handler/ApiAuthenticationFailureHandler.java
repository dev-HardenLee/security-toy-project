package com.example.test.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.test.dto.response.ResponseDTO;
import com.example.test.service.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private final ResponseService responseService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		ResponseDTO responseDTO = null; 
		
		if(exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
			responseDTO = responseService.getFailResponseDTO("security.invalid.user.code", "security.invalid.user.msg");
		}else if(exception instanceof InsufficientAuthenticationException) {
			responseDTO = responseService.getFailResponseDTO("security.insufficient.authenticaion.code", "security.insufficient.authenticaion.msg");
		}else {
			exception.printStackTrace();
			
			responseDTO = responseService.getFailResponseDTO("fail.code", "fail.msg");
		}// if-else
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		
		objectMapper.writeValue(response.getWriter(), responseDTO);
	}// onAuthenticationFailure
	
}// ApiAuthenticationFauilureHandler
















