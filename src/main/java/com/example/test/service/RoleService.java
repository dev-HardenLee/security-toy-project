package com.example.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.test.entity.Role;
import com.example.test.enumeration.RoleType;
import com.example.test.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	public String readRoleHierarchy() {
		StringBuilder sb = new StringBuilder();
		
		List<Role> roles = roleRepository.findAll();
		
		for (Role role : roles) {
			if(role.getParentRole() == null) continue;
			
			RoleType parentRoleType = role.getParentRole().getRoleType();
			RoleType roleType       = role.getRoleType();
			
			sb.append(parentRoleType.getRole() + " > " + roleType.getRole() + "\n");
		}// for
		
		return sb.toString();
	}// readRoleHierarchy
	
}// RoleService




















