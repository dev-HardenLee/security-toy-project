package com.example.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.test.dto.ResourceDTO;
import com.example.test.dto.RoleDTO;
import com.example.test.entity.Resource;
import com.example.test.entity.ResourceRole;
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

	public List<ResourceDTO> getAllResourceList() {
		LinkedHashMap<Resource, List<Role>> resourceMap = new LinkedHashMap<>();

		return null;
	}// getAllResourceList

	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getApiResourceMap() {
		LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap = new LinkedHashMap<>();

		List<Resource> resourceList = resourceRepository.findByResourceTypeWithRole(ResourceType.API_RESOURCE);

		for (Resource resource : resourceList) {
			RequestMatcher requestMatcher = new AntPathRequestMatcher(resource.getRequestMatcher(), resource.getHttpMethod());

			List<ConfigAttribute> roleList = new ArrayList<>();

			for (ResourceRole resourceRole : resource.getResourceRoleList()) {
				String roleName = resourceRole.getRole().getRoleType();

				roleList.add(new SecurityConfig(roleName));
			}// for

			resourceMap.put(requestMatcher, roleList);
		}// for
		
		return resourceMap;
	}// getApiResourceMap
	
}// ResourceService




































