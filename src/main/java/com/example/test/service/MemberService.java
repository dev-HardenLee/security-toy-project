package com.example.test.service;

import com.example.test.dto.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.test.dto.MemberDTO;
import com.example.test.entity.Member;
import com.example.test.entity.Role;
import com.example.test.exception.user.UserAlreadyExistException;
import com.example.test.repository.MemberRepository;
import com.example.test.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class MemberService {
	
	private final RoleRepository roleRepository;
	
	private final MemberRepository memberRepository;
	
	private final PasswordEncoder passwordEncoder;

	private final ModelMapper modelMapper;

	@Transactional
	public Member joinMember(MemberDTO.JoinDTO joinDTO) throws UserAlreadyExistException {
		if(memberRepository.findByUserId(joinDTO.getUserId()).isPresent()) throw new UserAlreadyExistException("This id is already use ID :" + joinDTO.getUserId());

		Role   role   = roleRepository.findByRoleType(joinDTO.getRoleType()).get();
		Member member = Member.builder()
				.name(joinDTO.getName())
				.userId(joinDTO.getUserId())
				.password(passwordEncoder.encode(joinDTO.getPassword()))
				.role(role)
				.build();
		
		memberRepository.save(member);
		
		return member;
	}// createMember

	public MemberDTO retriveMember(String userId) {
		Member member = memberRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Not exist user. ID = " + userId));

		MemberDTO memberDTO = modelMapper.map(member          , MemberDTO.class);
		RoleDTO   roleDTO   = modelMapper.map(member.getRole(), RoleDTO.class);

		memberDTO.setRoleDTO(roleDTO);

		return memberDTO;
	}// userId

}// MemberService



















