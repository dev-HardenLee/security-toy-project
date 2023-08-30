package com.example.test.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.test.entity.Resource;
import com.example.test.entity.Role;
import com.example.test.enumeration.ResourceType;
import com.example.test.repository.ResourceRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ResourceService {

	private final ResourceRepository resourceRepository;

	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getApiResourceMap() {
		LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap = new LinkedHashMap<>();

		List<Object[]> resourceList = resourceRepository.findByResourceTypeWithRole(ResourceType.API_RESOURCE);

		for (Object[] object : resourceList) {
			Resource resource = (Resource) object[0];
			Role     role     = (Role    ) object[2];

			RequestMatcher requestMatcher = new AntPathRequestMatcher(resource.getRequestMatcher(), resource.getHttpMethod());

			List<ConfigAttribute> configAttributes = Arrays.asList(new SecurityConfig(role.getRoleType()));

			resourceMap.put(requestMatcher, configAttributes);
		}// for
		
		return resourceMap;
	}// getApiResourceMap
	
}// ResourceService




































