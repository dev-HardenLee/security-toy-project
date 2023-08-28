package com.example.test.security.provider;

import com.example.test.dto.MemberDTO;
import com.example.test.enumeration.RoleType;
import com.example.test.security.authentication.AdminWebAuthenticationDetails;
import com.example.test.security.authentication.ApiCustomUser;
import com.example.test.security.exception.AdminKeyNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Log4j2
@RequiredArgsConstructor
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal()   == null ? "" : (String) authentication.getPrincipal();
        String password = authentication.getCredentials() == null ? "" : (String) authentication.getCredentials();

        ApiCustomUser userDetails = (ApiCustomUser) userDetailsService.loadUserByUsername(username);
        MemberDTO     memberDTO   = userDetails.getMemberDTO();

        if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Bad credentials Exception. Please check your password.");

        AdminWebAuthenticationDetails details = (AdminWebAuthenticationDetails) authentication.getDetails();

        log.info("details : " + details);

        if(!StringUtils.hasText(details.getAdminKey())) throw new AdminKeyNotExistException("Admin-Key is not exist. Please forward admin-key to the server");

        RoleType roleType = memberDTO.getRoleDTO().getRoleType();

        return new UsernamePasswordAuthenticationToken(memberDTO, null, Arrays.asList(new SimpleGrantedAuthority(roleType.getRole())));
    }// authenticate

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }// supports

}// AdminAuthenticationProvider
