package com.example.test.repository;

import com.example.test.entity.ResourceRole;
import com.example.test.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResourceRoleRepository extends JpaRepository<ResourceRole, Long> {

    @Query("DELETE FROM ResourceRole resourceRole")
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    public void bulkDeleteAll();

}// ResourceRoleRepository
