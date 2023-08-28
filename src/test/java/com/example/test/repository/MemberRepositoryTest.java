package com.example.test.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.entity.Member;

import lombok.extern.log4j.Log4j2;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@Log4j2
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void findByUserIdTest() {
		Member member = memberRepository.findByUserId("harden123").get();
	}// findByUserIdTest
	
}// MemberRepositoryTest

























