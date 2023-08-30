package com.example.test.service;

import java.util.List;

import com.example.test.dto.OrgChartDTO;
import com.example.test.dto.RoleDTO;
import com.example.test.util.RoleOrgChartUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.test.entity.Role;
import com.example.test.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleService {
	
	private final RoleRepository roleRepository;

	private final ModelMapper modelMapper;

	public Role addRole(RoleDTO.RequestRoleDTO requestRoleDTO) {
		Role parentRole = roleRepository.findById(requestRoleDTO.getParentRoleId()).get();
		Role childRole  = Role.builder()
				.roleType(requestRoleDTO.getRoleType())
				.parentRole(parentRole)
				.build();

		roleRepository.save(childRole);

		// TODO: 2023-08-30 Update role hierarchy

		return childRole;
	}// addRole

	public String makeRoleHierarchy() {
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

	public OrgChartDTO makeOrgChartDTO() {
		List<Role> roleList = roleRepository.findAll();

		RoleOrgChartUtil roleOrgChartUtil = new RoleOrgChartUtil(roleList);

		return roleOrgChartUtil.createOrgChartDTO();
	}// getOrgChartDTO


	
}// RoleService




















