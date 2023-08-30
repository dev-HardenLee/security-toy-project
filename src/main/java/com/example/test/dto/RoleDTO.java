package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RoleDTO {
	
	private Long id;
	
	private String roleType;
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class RequestRoleDTO {
		private Long parentRoleId;

		private String roleType;
	}// RequestRoleDTO

}// RoleDTO
