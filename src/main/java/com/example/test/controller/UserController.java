package com.example.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.dto.MemberDTO;
import com.example.test.dto.response.MultiDataResponseDTO;
import com.example.test.dto.response.SingleDataResponseDTO;
import com.example.test.service.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController { 
	
	private final ResponseService responseService;
	
	@GetMapping("/user")
	public ResponseEntity<SingleDataResponseDTO<MemberDTO>> getUserInfo(@AuthenticationPrincipal MemberDTO memberDTO) {
		SingleDataResponseDTO<MemberDTO> singleDataResponseDTO = responseService.getSingleSuccessResponseDTO(memberDTO);
		
		return new ResponseEntity<>(singleDataResponseDTO, HttpStatus.OK);
	}// SingleDataResponseDTO
	
}// UserController









