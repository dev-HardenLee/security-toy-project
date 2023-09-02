package com.example.test.repository;

import com.example.test.entity.Resource;
import com.example.test.enumeration.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long>, ResourceRepositoryCustom{

    @Query(
            "SELECT DISTINCT resource " +
            "FROM Resource resource " +
            "LEFT JOIN FETCH resource.resourceRoleList resourceRole " +
            "LEFT JOIN FETCH resourceRole.role role " +
            "WHERE 1=1 " +
            "AND resource.resourceType = :resourceType"
    )
    public List<Resource> findByResourceTypeWithRole(@Param(value = "resourceType") ResourceType resourceType);

}// ResourceRepository
