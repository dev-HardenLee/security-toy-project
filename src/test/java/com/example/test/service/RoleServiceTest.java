package com.example.test.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class RoleServiceTest {
	
	@Autowired
	private RoleService roleService;

	@Test
	void readRoleHierarchy() {
		String roleHierarchy = roleService.readRoleHierarchy();
		
		log.info("\n" + roleHierarchy);
	}// readRoleHierarchy

}// test
