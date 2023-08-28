package com.example.test.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class MultiDataResponseDTO <T> extends ResponseDTO {
	
	private List<T> dataList;
	
}// MultiDataResponseDTO
