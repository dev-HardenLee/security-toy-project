package com.example.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.dto.MemberDTO;
import com.example.test.dto.response.ResponseDTO;
import com.example.test.entity.Member;
import com.example.test.exception.join.UserAlreadyExistException;
import com.example.test.service.MemberService;
import com.example.test.service.ResponseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class JoinController {
	
	private final MemberService memberService;

	private final ResponseService responseService;
	
	@PostMapping("/join")
	public ResponseEntity<ResponseDTO> joinMember(@RequestBody MemberDTO.JoinDTO joinDTO) throws UserAlreadyExistException {
		Member member = memberService.createMember(joinDTO);
		
		ResponseDTO responseDTO = responseService.getSuccessResponseDTO(); 
		
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}// joinMember
	
}// JoinController
























