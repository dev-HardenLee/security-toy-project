package com.example.test.service;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.test.dto.response.MultiDataResponseDTO;
import com.example.test.dto.response.ResponseDTO;
import com.example.test.dto.response.SingleDataResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponseService {
	
	private final String successCode = "success.code";
	
	private final String successMsg = "success.msg";
	
	private final MessageSource messageSource;
	
	public ResponseDTO getSuccessResponseDTO() {
		ResponseDTO responseDTO = new ResponseDTO();
		
		responseDTO.setCode(getMessage(successCode));
		responseDTO.setMsg(getMessage(successMsg));
		
		return responseDTO;
	}// getSuccessResponseDTO
	
	public <T> SingleDataResponseDTO<T> getSingleSuccessResponseDTO(T data) {
		SingleDataResponseDTO<T> singleDataResponseDTO = new SingleDataResponseDTO<>();
		
		singleDataResponseDTO.setCode(getMessage(successCode));
		singleDataResponseDTO.setMsg(getMessage(successMsg));
		singleDataResponseDTO.setData(data);
		
		return singleDataResponseDTO;
	}// getSingleSuccessResponseDTO
	
	public<T> MultiDataResponseDTO<T> getMultiSuccessResponseDTO(List<T> dataList) {
		MultiDataResponseDTO<T> multiDataResponseDTO = new MultiDataResponseDTO<>();
		
		multiDataResponseDTO.setCode(getMessage(successCode));
		multiDataResponseDTO.setMsg(getMessage(successMsg));
		multiDataResponseDTO.setDataList(dataList);
		
		return multiDataResponseDTO;
	}// getMultiSuccessResponseDTO
	
	public ResponseDTO getFailResponseDTO(String code, String msg) {
		ResponseDTO responseDTO = new ResponseDTO();
		
		responseDTO.setCode(getMessage(code));
		responseDTO.setMsg(getMessage(msg));
		
		return responseDTO;
	}// getFailResponseDTO

	public ResponseDTO getFailResponseDTO(HttpStatus httpStatus) {
		ResponseDTO responseDTO = new ResponseDTO();

		responseDTO.setCode(String.valueOf(httpStatus.value()));
		responseDTO.setMsg(httpStatus.getReasonPhrase());

		return responseDTO;
	}// getFailResponseDTO
	
	private String getMessage(String message) {
		return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
	}// getMessage
	
}// ResponseService











