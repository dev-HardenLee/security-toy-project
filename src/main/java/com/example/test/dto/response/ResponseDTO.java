package com.example.test.dto.response;

import lombok.Data;

@Data
public class ResponseDTO {
	
	private String code;
	
	private String msg;
	
	private String detailMsg;

	public ResponseDTO() {
		super();
	}
	
	
	
}// ResponseDTO
