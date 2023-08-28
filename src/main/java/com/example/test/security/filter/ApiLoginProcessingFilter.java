package com.example.test.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.test.dto.MemberDTO;
import com.example.test.security.authentication.RequestAuthenticationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private ObjectMapper objectMapper = new ObjectMapper();

	public ApiLoginProcessingFilter(String url, HttpMethod httpMethod) {
		super(new AntPathRequestMatcher(url, httpMethod.name()));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		RequestAuthenticationDTO requestAuthenticationDTO = objectMapper.readValue(request.getReader(), RequestAuthenticationDTO.class);
		
		String username = requestAuthenticationDTO.getUserId()   == null ? "" : requestAuthenticationDTO.getUserId();
		String password = requestAuthenticationDTO.getPassword() == null ? "" : requestAuthenticationDTO.getPassword();
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		setDetails(request, authRequest);
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}// attemptAuthentication
	
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}
	
}// ApiUsernamePasswordAuthenticationFilter

























