package com.example.test.repository;

import java.util.List;
import java.util.Optional;

import com.example.test.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.test.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	@Query(
			"SELECT m " + 
			"FROM Member m " +
			"LEFT JOIN FETCH m.role " +
			"WHERE 1=1 " + 
			"AND m.userId = :userId "
	)
	Optional<Member> findByUserId(@Param(value = "userId") String userId);

	public List<Member> findByRole(Role role);

	@Query("DELETE FROM Member m")
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	public void bulkDeleteAll();

}// MemberRepository
