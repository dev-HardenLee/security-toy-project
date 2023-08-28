package com.example.test.security.handler;

import com.example.test.security.exception.AdminKeyNotExistException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errmsg = "Invalid your username or password";

        if(exception instanceof BadCredentialsException) {
            errmsg = "Invalid your username or password";
        }else if(exception instanceof AdminKeyNotExistException) {
            errmsg = "Please forward admin-key to the server";
        }else if(exception instanceof InsufficientAuthenticationException) {
            errmsg = "Insufficient Authentication";
        }// if-else

        String url = "/admin/login?error=true&errmsg=" + errmsg;

        redirectStrategy.sendRedirect(request, response, url);
    }// onAuthenticationFailure

}// AdminAuthenticationFailureHandler

















