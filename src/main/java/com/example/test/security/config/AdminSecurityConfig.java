package com.example.test.security.config;

import com.example.test.security.authentication.AdminWebAuthenticationDetailsSource;
import com.example.test.security.handler.AdminAuthenticationFailureHandler;
import com.example.test.security.provider.AdminAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Order(1)
@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
@Log4j2
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    // ======== Service
    private final UserDetailsService userDetailsService;

    // ======== Encoder
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }// configure

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }// configure

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .mvcMatcher("/admin/**")
                .authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .anyRequest().hasRole("ADMIN");

        http
                .formLogin()
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .authenticationDetailsSource(adminAuthenticationDetailsSource())
                .failureHandler(adminAuthenticationFailureHandler())
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/admin/home");
                });

    }// configure

    // ============== Provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        AdminAuthenticationProvider authenticationProvider = new AdminAuthenticationProvider(userDetailsService, passwordEncoder);

        return authenticationProvider;
    }// authenticationProvider

    // ============== Handler
    @Bean
    public AuthenticationFailureHandler adminAuthenticationFailureHandler() {
        AdminAuthenticationFailureHandler authenticationFailureHandler = new AdminAuthenticationFailureHandler();

        return authenticationFailureHandler;
    }// authenticationFailureHandler

    // ============== WebDetails
    @Bean
    public AuthenticationDetailsSource adminAuthenticationDetailsSource()  {
        AuthenticationDetailsSource adminAuthenticationDetailsSource = new AdminWebAuthenticationDetailsSource();

        return adminAuthenticationDetailsSource;
    }// adminAuthenticationDetailsSource

}// AdminSecurityConfig




































