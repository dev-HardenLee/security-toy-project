package com.example.test.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.test.dto.response.SingleDataResponseDTO;
import com.example.test.dto.response.TokenDTO;
import com.example.test.security.exception.JwtRefreshTokenNotEqualsException;
import com.example.test.security.exception.JwtTokenExpiredException;
import com.example.test.security.exception.JwtTokenExtractException;
import com.example.test.security.exception.JwtTokenNotFoundException;
import com.example.test.security.handler.ApiJwtTokenExceptionHandler;
import com.example.test.security.provider.ApiJwtTokenProvider;
import com.example.test.security.service.RedisService;
import com.example.test.service.ResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiJwtRefreshTokenFilter extends OncePerRequestFilter {
	
	private final RequestMatcher requestMatcher;
	
	private final ApiJwtTokenProvider apiJwtTokenProvider;
	
	private final ApiJwtTokenExceptionHandler apiJwtTokenExceptionHandler;
	
	private final ApiJwtTokenProvider jwtTokenProvider;
	
	private final ResponseService responseService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(!requestMatcher.matches(request) || !request.getMethod().equals(HttpMethod.POST.name())) {
			filterChain.doFilter(request, response);
			
			return;
		}// if
		
		TokenDTO tokenDTO = objectMapper.readValue(request.getReader(), TokenDTO.class);
		
		if(tokenDTO.getAccessToken() == null || tokenDTO.getRefreshToken() == null) {
			apiJwtTokenExceptionHandler.exceptionHandle(request, response, new JwtTokenNotFoundException("Try Refresh JWT Token But Not Exist."));
			
			return;
		}// if
		
		String username = null;
		
		try {
			username = apiJwtTokenProvider.extractClaims(tokenDTO.getAccessToken()).getSubject();
		} catch (ExpiredJwtException e) {
			username = e.getClaims().getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			
			apiJwtTokenExceptionHandler.exceptionHandle(request, response, new JwtTokenExtractException("Refresh Token Extract Exception"));
			
			return;
		}// try-catch
		
		if(apiJwtTokenProvider.isExpiredRefreshToken(tokenDTO.getRefreshToken())) {
			apiJwtTokenExceptionHandler.exceptionHandle(request, response, new JwtTokenExpiredException("JWT Refresh Token Expired!!"));
			
			return;
		}// if
		
		String savedRefreshToken = jwtTokenProvider.getRefreshToken(username);
		
		if(!tokenDTO.getRefreshToken().equals(savedRefreshToken)) {
			apiJwtTokenExceptionHandler.exceptionHandle(request, response, new JwtRefreshTokenNotEqualsException("Refresh Token Does Not Equal By Parameter"));
			
			return;
		}// if
		
		TokenDTO newToken = jwtTokenProvider.createJwtToken(username);
		
		SingleDataResponseDTO<TokenDTO> responseDTO = responseService.getSingleSuccessResponseDTO(newToken);
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
		
		objectMapper.writeValue(response.getWriter(), responseDTO);
	}// doFilterInternal
	
}// ApiJwtRefreshTokenFilter


















