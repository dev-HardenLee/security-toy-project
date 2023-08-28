package com.example.test.security.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.test.dto.MemberDTO;

import lombok.Getter;

@Getter
public class ApiCustomUser extends User {

	private MemberDTO memberDTO;
	
	public ApiCustomUser(MemberDTO memberDTO, String password, String roleType) {
		this(memberDTO.getUserId(), password, Arrays.asList(new SimpleGrantedAuthority(roleType)));
		this.memberDTO = memberDTO;
	}// constructor
	
	private ApiCustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}// constructor
	
}// ApiCustomUser
