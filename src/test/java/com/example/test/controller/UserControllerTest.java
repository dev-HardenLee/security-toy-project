package com.example.test.controller;

import com.example.test.dto.MemberDTO;
import com.example.test.entity.Member;
import com.example.test.entity.Role;
import com.example.test.repository.MemberRepository;
import com.example.test.repository.RoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import javax.xml.transform.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        memberRepository.bulkDeleteAll();
    }// setUp
    @Test
    @DisplayName("joinMember : 회원가입에 성공한다.")
    @WithAnonymousUser
    public void joinMember() throws Exception {
        // given
        MemberDTO.JoinDTO joinDTO = makeJoin();

        // when
        ResultActions result = mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(joinDTO))
        );

        // then
        result.andExpect(status().isCreated()).andDo(print());

        Member member = memberRepository.findByUserId(joinDTO.getUserId()).get();

        assertThat(member.getUserId()).isEqualTo(joinDTO.getUserId());
    }// joinMember

    @Test
    @DisplayName("joinMember : 중복된 아이디는 회원가입에 실패한다.")
    public void joinMemberDuplicationTest() throws Exception{
        // given
        Member member = makeMember();

        memberRepository.save(member);

        MemberDTO.JoinDTO joinDTO = makeJoin();

        // when
        ResultActions result = mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(joinDTO))
        );

        // then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("-101"))
                .andExpect(jsonPath("$.msg").value("The UserId is exist already"));
    }// joinMemberDuplicationTest
    @DisplayName("retriveUser : 유저 검색에 성공한다.")
    @WithMockUser(username = "harden", roles = {"USER"})
    @Test
    public void retriveUser() throws Exception{
        // given
        Member member = makeMember();

        memberRepository.save(member);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/user/{userId}", member.getUserId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(member.getUserId()))
                .andDo(print());
    }// retriveUser

    public MemberDTO.JoinDTO makeJoin() {
        MemberDTO.JoinDTO joinDTO = MemberDTO.JoinDTO.builder()
                .userId("harden1234")
                .password("1234")
                .name("harden")
                .roleType("ROLE_USER")
                .build();

        return joinDTO;
    }// makeJoin

    public Member makeMember() {
        Role userRole = roleRepository.findByRoleType("ROLE_USER").get();

        Member member = Member.builder()
                .userId("harden1234")
                .password("1234")
                .name("harden")
                .role(userRole)
                .build();

        return member;
    }// makeMember

}// UserControllerTest





































