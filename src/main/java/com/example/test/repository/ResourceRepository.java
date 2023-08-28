package com.example.test.repository;

import com.example.test.entity.Resource;
import com.example.test.enumeration.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query(
            "SELECT resource, resourceRole, role " +
            "FROM Resource resource " +
            "LEFT JOIN ResourceRole resourceRole ON resourceRole.resource = resource " +
            "LEFT JOIN Role         role         ON resourceRole.role     = role " +
            "WHERE 1=1 " +
            "AND resource.resourceType = :resourceType"
    )
    public List<Object[]> findByResourceTypeWithRole(@Param(value = "resourceType") ResourceType resourceType);


}// ResourceRepository
