package com.example.test.service;

import java.util.List;
import java.util.Optional;

import com.example.test.dto.OrgChartDTO;
import com.example.test.dto.RoleDTO;
import com.example.test.entity.Member;
import com.example.test.entity.ResourceRole;
import com.example.test.exception.RoleApiException;
import com.example.test.exception.role.ChildRoleExistException;
import com.example.test.exception.role.RoleAlreadyExistException;
import com.example.test.repository.MemberRepository;
import com.example.test.repository.ResourceRoleRepository;
import com.example.test.security.rolehierarcy.CustomRoleHierarcyImpl;
import com.example.test.util.RoleOrgChartUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.test.entity.Role;
import com.example.test.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class RoleService {

	private final CustomRoleHierarcyImpl customRoleHierarchyImpl;

	private final RoleRepository roleRepository;

	private final ResourceRoleRepository resourceRoleRepository;

	private final MemberRepository memberRepository;

	private final ModelMapper modelMapper;

	@Transactional
	public Role addRole(RoleDTO.RequestRoleDTO requestRoleDTO) throws RoleApiException {
		Optional<Role> opRole = roleRepository.findByRoleType(requestRoleDTO.getRoleType());

		if(opRole.isPresent()) throw new RoleAlreadyExistException(requestRoleDTO.getRoleType() + " is already exist.");

		Role parentRole = roleRepository.findById(requestRoleDTO.getParentRoleId()).get();
		Role childRole  = Role.builder()
				.roleType(requestRoleDTO.getRoleType())
				.parentRole(parentRole)
				.build();

		roleRepository.save(childRole);

		return childRole;
	}// addRole

	public void reloadRoleHierarchy() {
		String hierarchy = makeRoleHierarchy();

		customRoleHierarchyImpl.setHierarchy(hierarchy);
	}// reloadRoleHierarchy

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

	@Transactional
	public Role updateRole(RoleDTO.RequestUpdateRoleDTO requestDTO) throws RoleApiException {
		Role role = roleRepository.findById(requestDTO.getRoleId()).get();

		if(requestDTO.getParentRoleId() != null) {
			Role parentRole = roleRepository.findById(requestDTO.getParentRoleId()).get();

			role.changeParent(parentRole);
		}// if

		if(StringUtils.hasText(requestDTO.getRoleType())) {
			Optional<Role> opRole = roleRepository.findByRoleType(requestDTO.getRoleType());

			if(opRole.isPresent()) throw new RoleAlreadyExistException(requestDTO.getRoleType() + " is already exist.");

			role.changeRoleType(requestDTO.getRoleType());
		}// if

		return role;
	}// changeParentRole

	
}// RoleService




















