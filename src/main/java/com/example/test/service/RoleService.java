package com.example.test.service;

import java.util.List;

import com.example.test.dto.OrgChartDTO;
import com.example.test.exception.role.RoleAlreadyExistException;
import com.example.test.util.RoleOrgChartUtil;
import org.springframework.stereotype.Service;

import com.example.test.entity.Role;
import com.example.test.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

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
			
			String parentRoleType = role.getParentRole().getRoleType();
			String roleType       = role.getRoleType();
			
			sb.append(parentRoleType + " > " + roleType + "\n");
		}// for
		
		return sb.toString();
	}// readRoleHierarchy

	public OrgChartDTO getOrgChartDTO() {
		List<Role> roleList = roleRepository.findAll();

		RoleOrgChartUtil roleOrgChartUtil = new RoleOrgChartUtil(roleList);

		return roleOrgChartUtil.createOrgChartDTO();
	}// getOrgChartDTO

	@Transactional
	public Role addRole(Long parentRoleId, String newRoleType) {
		if(roleRepository.findByRoleType(newRoleType).isPresent()) throw new RoleAlreadyExistException("Your required role is already exist");
		
		Role parentRole = roleRepository.findById(parentRoleId).get();

		Role newRole = Role.builder()
				.parentRole(parentRole)
				.roleType(newRoleType)
				.build();

		roleRepository.save(newRole);

		return newRole;
	}// addRole
	
}// RoleService




















