package com.example.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.test.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	@Query(
			"SELECT m " + 
			"FROM Member m " +
			"JOIN FETCH m.role " +
			"WHERE 1=1 " + 
			"AND m.userId = :userId "
	)
	Optional<Member> findByUserId(@Param(value = "userId") String userId);
	
}// MemberRepository
