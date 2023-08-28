package com.example.test.security.authentication;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class AdminWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, AdminWebAuthenticationDetails> {
    @Override
    public AdminWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new AdminWebAuthenticationDetails(context);
    }// buildDetails

}// AdminWebAuthenticationDetailsSource
