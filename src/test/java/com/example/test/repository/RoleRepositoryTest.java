package com.example.test.repository;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.entity.Role;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Transactional
@Log4j2
class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ResourceRoleRepository resourceRoleRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void findAllWithParentSort() {
		List<Role> role = roleRepository.findAll();
		
		role.forEach(r -> log.info(r + " --> " + r.getParentRole()));
	}// findAllWithParent
}// RoleRepositoryTest











