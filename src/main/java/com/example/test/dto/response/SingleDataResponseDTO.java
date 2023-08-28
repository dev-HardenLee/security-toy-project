package com.example.test.dto.response;

import lombok.Data;

@Data
public class SingleDataResponseDTO <T> extends ResponseDTO {
	
	private T data;
	
}// SingleDataResponseDTO
