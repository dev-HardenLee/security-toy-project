package com.example.test.security.mastersource;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class ApiFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		
		for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : this.resourceMap.entrySet()) {
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}// if
		}
		return null;
	}// getAttributes

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<>();
		this.resourceMap.values().forEach(allAttributes::addAll);
		return allAttributes;
	}// getAllConfigAttributes

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
}// CustomFilterInvocationSecurityMetadataSource
