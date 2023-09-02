package com.example.test.controller;

import com.example.test.annotation.ApiController;
import com.example.test.dto.response.ResponseDTO;
import com.example.test.entity.Member;
import com.example.test.exception.user.UserAlreadyExistException;
import com.example.test.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.test.dto.MemberDTO;
import com.example.test.dto.response.SingleDataResponseDTO;
import com.example.test.service.ResponseService;

import lombok.RequiredArgsConstructor;

@ApiController
@RequiredArgsConstructor
public class UserController {

	private final ResponseService responseService;

	private final MemberService memberService;

	@PostMapping("/user")
	public ResponseEntity<ResponseDTO> joinMember(@RequestBody MemberDTO.JoinDTO joinDTO) throws UserAlreadyExistException {
		Member member = memberService.joinMember(joinDTO);

		ResponseDTO responseDTO = responseService.getSuccessResponseDTO();

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseDTO);

	}// joinMember

	@GetMapping("/user/{userId}")
	public ResponseEntity<SingleDataResponseDTO<MemberDTO>> retriveUser(@PathVariable String userId) {
		MemberDTO memberDTO = memberService.retriveMember(userId);

		SingleDataResponseDTO<MemberDTO> singleDataResponseDTO = responseService.getSingleSuccessResponseDTO(memberDTO);
		
		return new ResponseEntity<>(singleDataResponseDTO, HttpStatus.OK);
	}// SingleDataResponseDTO
	
}// UserController









