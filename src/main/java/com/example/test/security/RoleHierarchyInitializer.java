package com.example.test.security;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.test.security.rolehierarcy.CustomRoleHierarcyImpl;
import com.example.test.service.RoleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleHierarchyInitializer implements ApplicationRunner {
	
	private final RoleService roleService;
	
	private final CustomRoleHierarcyImpl customRoleHierarchyImpl;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		String roleHierarchy = roleService.makeRoleHierarchy();
		
		customRoleHierarchyImpl.setHierarchy(roleHierarchy);
	}// run
	
	
	
}// RoleHierarchyInitializer




















