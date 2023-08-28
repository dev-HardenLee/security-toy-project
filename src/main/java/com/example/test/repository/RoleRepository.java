package com.example.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.entity.Role;
import com.example.test.enumeration.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<Role> findByRoleType(RoleType roleType);
	
	@EntityGraph(attributePaths = "parentRole", type = EntityGraphType.FETCH)
	public List<Role> findAll();
	
}// RoleRepository
