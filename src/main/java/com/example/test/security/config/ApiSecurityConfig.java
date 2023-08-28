package com.example.test.security.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.test.security.factory.ApiUrlResourceRequestMapFactoryBean;
import com.example.test.security.filter.ApiJwtRefreshTokenFilter;
import com.example.test.security.filter.ApiJwtTokenAuthenticationFilter;
import com.example.test.security.filter.ApiLoginProcessingFilter;
import com.example.test.security.handler.ApiAccessDeniedHandler;
import com.example.test.security.handler.ApiAuthenticationEntryPointHandler;
import com.example.test.security.handler.ApiAuthenticationFailureHandler;
import com.example.test.security.handler.ApiAuthenticationSuccessHandler;
import com.example.test.security.handler.ApiJwtTokenExceptionHandler;
import com.example.test.security.mastersource.ApiFilterInvocationSecurityMetadataSource;
import com.example.test.security.provider.ApiAuthenticationProvider;
import com.example.test.security.provider.ApiJwtTokenProvider;
import com.example.test.security.rolehierarcy.CustomRoleHierarcyImpl;
import com.example.test.security.service.RedisService;
import com.example.test.service.ResourceService;
import com.example.test.service.ResponseService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = false)
@Order(0)
@RequiredArgsConstructor
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// ======================= Service
	private final UserDetailsService userDetailsService;
	
	private final ResponseService responseService;
	
	private final ResourceService resourceService;
	
	private final RedisService redisService;
	
	// ======================= Role Hierarchy
	private final CustomRoleHierarcyImpl customRoleHierarcyImpl;

	// ======== Encoder
	private final PasswordEncoder passwordEncoder;

	// ======================= Static object
	private final static String LOGIN_URL = "/api/login";
	
	private final static String REFRESH_TOKEN_URL = "/api/refreshtoken";
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(apiAuthenticationProvider());
	}// configure

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}// configure

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin().disable();
		http.httpBasic().disable();
		http.logout().disable();
		
		http
				.mvcMatcher("/api/**")
				.authorizeRequests()
				.anyRequest().authenticated()
						.and()
				.addFilterBefore(apiUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(apiJwtRefreshTokenFilter()               , UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(apiJwtTokenAuthenticationFilter()        , UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(apiFilterSecurityInterceptor()           , FilterSecurityInterceptor.class);
		
		http
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler())
				.authenticationEntryPoint(authenticationEntryPoint());
		
		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}// configure
	
	// =============== Filter Bean
	@Bean
	public ApiLoginProcessingFilter apiUsernamePasswordAuthenticationFilter() throws Exception {
		ApiLoginProcessingFilter apiUsernamePasswordAuthenticationFilter = new ApiLoginProcessingFilter(LOGIN_URL, HttpMethod.POST);
		
		apiUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		apiUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		apiUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		
		return apiUsernamePasswordAuthenticationFilter;
	}// apiUsernamePasswordAuthenticationFilter
	
	@Bean
	public ApiJwtTokenAuthenticationFilter apiJwtTokenAuthenticationFilter() {
		ApiJwtTokenAuthenticationFilter apiJwtTokenAuthenticationFilter = new ApiJwtTokenAuthenticationFilter(apiJwtTokenProvider(), userDetailsService, apiJwtTokenExpiredHandler());
		
		return apiJwtTokenAuthenticationFilter;
	}// apiJwtTokenAuthenticationFilter
	
	@Bean
	public ApiJwtRefreshTokenFilter apiJwtRefreshTokenFilter() {
		ApiJwtRefreshTokenFilter apiJwtRefreshTokenFilter = new ApiJwtRefreshTokenFilter(new AntPathRequestMatcher(REFRESH_TOKEN_URL), apiJwtTokenProvider(), apiJwtTokenExpiredHandler(), apiJwtTokenProvider(), responseService);
		
		return apiJwtRefreshTokenFilter;
	}// apiJwtRefreshTokenFilter
	
	// ================ Provider Bean
	@Bean
	public ApiAuthenticationProvider apiAuthenticationProvider() {
		ApiAuthenticationProvider apiAuthenticationProvider = new ApiAuthenticationProvider(userDetailsService, passwordEncoder);
		
		return apiAuthenticationProvider;
	}// apiAuthenticationProvider
	
	// ================ Handler Bean
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		ApiAuthenticationSuccessHandler apiAuthenticationSuccessHandler = new ApiAuthenticationSuccessHandler(apiJwtTokenProvider(), responseService);
		
		return apiAuthenticationSuccessHandler;
	}// authenticationSuccessHandler
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		ApiAuthenticationFailureHandler apiAuthenticationFailureHandler = new ApiAuthenticationFailureHandler(responseService);
		
		return apiAuthenticationFailureHandler;
	}// authenticationFauilureHandlewr
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		ApiAccessDeniedHandler accessDeniedHandler = new ApiAccessDeniedHandler(responseService);
		
		return accessDeniedHandler;
	}// accessDeniedHandler
	
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		ApiAuthenticationEntryPointHandler authenticationEntryPointHandler = new ApiAuthenticationEntryPointHandler(responseService);
		
		return authenticationEntryPointHandler;
	}// authenticationEntryPoint
	
	@Bean
	public ApiJwtTokenExceptionHandler apiJwtTokenExpiredHandler() {
		ApiJwtTokenExceptionHandler apiJwtTokenExpiredHandler = new ApiJwtTokenExceptionHandler(responseService);
		
		return apiJwtTokenExpiredHandler;
	}// apiJwtTokenExpiredHandler
	
	@Bean
	public ApiJwtTokenProvider apiJwtTokenProvider() {
		ApiJwtTokenProvider apiJwtTokenProvider = new ApiJwtTokenProvider(redisService);
		
		return apiJwtTokenProvider;
	}// apiJwtTokenProvider
	
	// ================ PasswordEncoder
	
	@Bean
	public FilterSecurityInterceptor apiFilterSecurityInterceptor() throws Exception {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		
		filterSecurityInterceptor.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource());
		filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
		filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
		
		return filterSecurityInterceptor;
	}// filterSecurityInterceptor
	
	@Bean
	public ApiFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource() throws Exception {
		ApiFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource = new ApiFilterInvocationSecurityMetadataSource(apiUrlResourceRequestMapFactoryBean().getObject());
		
		return customFilterInvocationSecurityMetadataSource;
	}// customFilterInvocationSecurityMetadataSource
	
	@Bean
	public ApiUrlResourceRequestMapFactoryBean apiUrlResourceRequestMapFactoryBean() {
		ApiUrlResourceRequestMapFactoryBean apiUrlResourceRequestMapFactoryBean = new ApiUrlResourceRequestMapFactoryBean(resourceService);

		return apiUrlResourceRequestMapFactoryBean;
	}// apiUrlResourceRequestMapFactoryBean
	
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		AffirmativeBased affirmativeBased = new AffirmativeBased(Arrays.asList(roleHierarcyVoter()));
		
		return affirmativeBased;
	}// accessDecisionManager
	
	@Bean
	public AccessDecisionVoter<?> roleHierarcyVoter() {
		RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(customRoleHierarcyImpl);
		
		return roleHierarchyVoter;
	}// roleHierarcyVoter
	
	
}// SecurityConfig














