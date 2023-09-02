package com.example.test.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.test.security.authentication.ApiCustomUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {
	
	private final UserDetailsService userDetailsService;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		// id check
		ApiCustomUser apiCustomUser = (ApiCustomUser) userDetailsService.loadUserByUsername(username);

		// password check
		if(!passwordEncoder.matches(password, apiCustomUser.getPassword())) throw new BadCredentialsException("Password is encorrect");
		
		return new UsernamePasswordAuthenticationToken(apiCustomUser, null, apiCustomUser.getAuthorities());
	}// authenticate

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}// supports
	
}// ApiAuthenticationProvider




















