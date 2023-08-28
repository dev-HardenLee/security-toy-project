package com.example.test.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import com.example.test.dto.response.ResponseDTO;
import com.example.test.security.exception.JwtTokenExpiredException;
import com.example.test.security.exception.JwtTokenExtractException;
import com.example.test.security.exception.JwtTokenNotFoundException;
import com.example.test.service.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiJwtTokenExceptionHandler {
	
	private final ResponseService responseService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public void exceptionHandle(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		
		ResponseDTO responseDTO = null;
		
		if(authException instanceof JwtTokenNotFoundException) {
			responseDTO = responseService.getFailResponseDTO("jwttoken.notfound.code", "jwttoken.notfound.msg");
		}else if(authException instanceof JwtTokenExtractException) {
			responseDTO = responseService.getFailResponseDTO("jwttoken.extract.exception.code", "jwttoken.extract.exception.msg");
		}else if(authException instanceof JwtTokenExpiredException){
			responseDTO = responseService.getFailResponseDTO("jwttoken.expired.code", "jwttoken.expired.msg");
		}else {
			authException.printStackTrace();
			
			responseDTO = responseService.getFailResponseDTO("fail.code", "fail.msg");
			
			responseDTO.setDetailMsg(authException.getMessage());
		}// if-else
		
		objectMapper.writeValue(response.getWriter(), responseDTO);
	}// jwtTokenExpiredHandle
	
}// JwtTokenExpiredHandler












