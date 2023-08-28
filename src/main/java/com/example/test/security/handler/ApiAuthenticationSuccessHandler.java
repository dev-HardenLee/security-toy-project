package com.example.test.security.handler;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.test.dto.response.SingleDataResponseDTO;
import com.example.test.dto.response.TokenDTO;
import com.example.test.security.authentication.ApiCustomUser;
import com.example.test.security.provider.ApiJwtTokenProvider;
import com.example.test.service.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private final ApiJwtTokenProvider jwtTokenProvider;
	
	private final ResponseService responseService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		ApiCustomUser apiCustomUser = (ApiCustomUser) authentication.getPrincipal();
		
		TokenDTO tokenDTO = jwtTokenProvider.createJwtToken(apiCustomUser.getUsername());
		
		SingleDataResponseDTO<TokenDTO> responseDTO = responseService.getSingleSuccessResponseDTO(tokenDTO);
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		
		objectMapper.writeValue(response.getWriter(), responseDTO);
	}// onAuthenticationSuccess
	
}// ApiAuthenticationSuccessHandler




















