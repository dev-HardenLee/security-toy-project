package com.example.test.repository;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.entity.Role;

import lombok.extern.log4j.Log4j2;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@Log4j2
class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	@Disabled
	@Rollback(value = false)
	void dummyInsert() {
		memberRepository.deleteAll();
		roleRepository.deleteAll();
		
		Role adminRole   = Role.builder().roleType("ROLE_ADMIN").build();
		
		roleRepository.save(adminRole);
		
		Role leaderRole  = Role.builder().roleType("ROLE_LEADER").parentRole(adminRole).build();
		Role systemRole  = Role.builder().roleType("ROLE_SYSTEM").parentRole(adminRole).build();
		
		roleRepository.save(leaderRole);
		roleRepository.save(systemRole);
		
		Role managerRole = Role.builder().roleType("ROLE_MANAGER").parentRole(leaderRole).build();
		
		roleRepository.save(managerRole);
		
		Role userRole = Role.builder().roleType("ROLE_USER").parentRole(managerRole).build();
		
		roleRepository.save(userRole);
		
		Role anonymousRole = Role.builder().roleType("ROLE_ANONYMOUS").parentRole(userRole).build();
		
		roleRepository.save(anonymousRole);
	}

	@Test
	void findAllWithParentSort() {
		List<Role> role = roleRepository.findAll();
		
		role.forEach(r -> log.info(r + " --> " + r.getParentRole()));
	}// findAllWithParent
}// RoleRepositoryTest











