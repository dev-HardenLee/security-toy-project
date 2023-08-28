package com.example.test.security.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.test.dto.MemberDTO;
import com.example.test.dto.RoleDTO;
import com.example.test.entity.Member;
import com.example.test.repository.MemberRepository;
import com.example.test.security.authentication.ApiCustomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	private final ModelMapper modelMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
		
		String password = member.getPassword();
		
		MemberDTO memberDTO = modelMapper.map(member          , MemberDTO.class);
		RoleDTO   roleDTO   = modelMapper.map(member.getRole(), RoleDTO.class  );
		
		memberDTO.setRoleDTO(roleDTO);
		
		return new ApiCustomUser(memberDTO, password, roleDTO.getRoleType());
	}// loadUserByUsername
	
}// ApiUserDetailsService













