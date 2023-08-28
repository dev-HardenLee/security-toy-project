package com.example.test.dto;



import lombok.Data;

@Data
public class MemberDTO {
	
	private Long id;
	
	private String name;
	
	private String userId;
	
	private RoleDTO roleDTO;
	
	@Data
	public static class JoinDTO {
		
		private String name;
		
		private String userId;
		
		private String password;
		
		private String roleType;
		
	}// JoinDTO
	
}// MemberDTO
