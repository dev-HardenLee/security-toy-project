package com.example.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Optional<Role> findByRoleType(String roleType);
	
	@EntityGraph(attributePaths = "parentRole", type = EntityGraphType.FETCH)
	public List<Role> findAll(Sort sort);
	
}// RoleRepository
