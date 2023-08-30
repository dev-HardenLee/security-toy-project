package com.example.test.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberDTO {
	
	private Long id;
	
	private String name;
	
	private String userId;
	
	private RoleDTO roleDTO;
	
	@Data
	@NoArgsConstructor
	public static class JoinDTO {
		
		private String name;
		
		private String userId;
		
		private String password;
		
		private String roleType;
		@Builder
		public JoinDTO(String name, String userId, String password, String roleType) {
			this.name = name;
			this.userId = userId;
			this.password = password;
			this.roleType = roleType;
		}

	}// JoinDTO
	
}// MemberDTO
