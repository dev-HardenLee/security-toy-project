package com.example.test.security.factory;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.example.test.service.ResourceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class ApiUrlResourceRequestMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>>{
	
	private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

	private final ResourceService resourceService;
	
	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
		if(resourceMap == null) {
			this.resourceMap = resourceService.getApiResourceMap();
		}// if
		
		return resourceMap;
	}// getObject

	@Override
	public Class<?> getObjectType() {
		return LinkedHashMap.class;
	}

	@Override
	public boolean isSingleton() {
		return FactoryBean.super.isSingleton();
	}
	
}// UrlResourceRequestMapFactoryBean





























