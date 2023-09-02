package com.example.test.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.test.dto.response.ResponseDTO;
import com.example.test.service.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

	private final ResponseService responseSerivce;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 해당 자원의 권한이 있는지 확인. 사용자의 권한 확인.
		ResponseDTO responseDTO = responseSerivce.getFailResponseDTO("security.access.denied.code", "security.access.denied.msg");
		
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		
		objectMapper.writeValue(response.getWriter(), responseDTO);
	}// handler

}// ApiAccessDeniedHandler
