package com.example.test.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.test.dto.response.ResponseDTO;
import com.example.test.exception.UserApiException;
import com.example.test.exception.user.UserAlreadyExistException;
import com.example.test.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class RestApiExceptionController {
	
	private final ResponseService responseService;
	
	@ExceptionHandler(value = UserApiException.class)
	public ResponseEntity<ResponseDTO> userApiException(UserApiException exception) {
		ResponseDTO responseDTO = null;
		
		if(exception instanceof UserAlreadyExistException) {
			responseDTO = responseService.getFailResponseDTO("api.user.existalready.code", "api.user.existalready.msg");
		}// if
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}// userApiException
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseDTO> exception(Exception exception) {
		exception.printStackTrace();
		
		ResponseDTO responseDTO = responseService.getFailResponseDTO("fail.code", "fail.msg", exception);
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}// userApiException
	
}// RestApiExceptionController


















