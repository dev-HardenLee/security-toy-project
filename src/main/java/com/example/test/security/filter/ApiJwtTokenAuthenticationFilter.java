package com.example.test.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.test.security.authentication.ApiCustomUser;
import com.example.test.security.exception.JwtTokenExpiredException;
import com.example.test.security.exception.JwtTokenExtractException;
import com.example.test.security.exception.JwtTokenNotFoundException;
import com.example.test.security.handler.ApiJwtTokenExceptionHandler;
import com.example.test.security.provider.ApiJwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class ApiJwtTokenAuthenticationFilter extends OncePerRequestFilter {
	
	private static final String HEADER_NAME = "Authorization";
	
	private static final String TOKEN_TYPE = "Bearer";
	
	private final ApiJwtTokenProvider apiJwtTokenProvider;
	
	private final UserDetailsService userDetailsService;
	
	private final ApiJwtTokenExceptionHandler apiJwtTokenExceptionHandler;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = extractTokenByRequestHeader(request);
		
		if(jwtToken == null) {
			filterChain.doFilter(request, response);
			
			return;
		}// if
		
		String        username      = null;
		ApiCustomUser apiCustomUser = null; 
		
		try {
			Claims claims = apiJwtTokenProvider.extractClaims(jwtToken);
			
			username      = claims.getSubject();
			apiCustomUser = (ApiCustomUser) userDetailsService.loadUserByUsername(username);  
			
		} catch (ExpiredJwtException e) {
			apiJwtTokenExceptionHandler.exceptionHandle(request, response, new JwtTokenExpiredException("Jwt Token Success But User Invalid"));
			
			return;
		} catch (Exception e) {
			e.printStackTrace();
			
			apiJwtTokenExceptionHandler.exceptionHandle(request, response, new JwtTokenExtractException("Token Extract Exception"));
			
			return;
		}// try-catch
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(apiCustomUser.getMemberDTO(), null, apiCustomUser.getAuthorities());
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authToken);
		SecurityContextHolder.setContext(context);
		
		filterChain.doFilter(request, response);
		
		return;
	}// doFilterInternal

	private String extractTokenByRequestHeader(HttpServletRequest request) {
		String authorization = request.getHeader(HEADER_NAME);
		
		if(StringUtils.isEmpty(authorization)) return null;
		
		String[] split = authorization.split(" ");
		
		if(split.length != 2) return null;
		if(!split[0].equals(TOKEN_TYPE)) return null;
		
		return split[1];
	}// extractTokenByRequestHeader
	
	
	
}// JwtTokenAuthenticationFilter












