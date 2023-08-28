package com.example.test.dto.response;

import lombok.Data;

@Data
public class TokenDTO {
	
	private String accessToken;
	
	private String refreshToken;
	
	public TokenDTO() {
		super();
	}
	
	public TokenDTO(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}	
	
}// TokenDTO
