package com.example.test.security.authentication;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

@Getter
public class AdminWebAuthenticationDetails extends WebAuthenticationDetails {

    protected final String KEY_NAME = "admin-key";
    private         String adminKey;


    public AdminWebAuthenticationDetails(HttpServletRequest request) {
        super(request);

        this.adminKey = request.getParameter(KEY_NAME);
    }

}
