package com.example.test.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.test.dto.response.ResponseDTO;
import com.example.test.service.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private final ResponseService responseService;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ResponseDTO responseDTO = null;
		
		if(authException instanceof InsufficientAuthenticationException) {
			responseDTO = responseService.getFailResponseDTO("security.anonymous.user.code", "security.anonymous.user.msg");
		}else {
			authException.printStackTrace();
			
			responseDTO = responseService.getFailResponseDTO("fail.code", "fail.msg");
			
			responseDTO.setDetailMsg(authException.getMessage());
		}// if-else
				
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		
		objectMapper.writeValue(response.getWriter(), responseDTO);
	}// commence

}// ApiAuthenticationEntryPointHandler
