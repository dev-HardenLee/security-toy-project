package com.example.test.controller.admin;

import com.example.test.dto.RoleDTO;
import com.example.test.entity.Member;
import com.example.test.entity.Resource;
import com.example.test.entity.ResourceRole;
import com.example.test.entity.Role;
import com.example.test.enumeration.ResourceType;
import com.example.test.repository.MemberRepository;
import com.example.test.repository.ResourceRepository;
import com.example.test.repository.ResourceRoleRepository;
import com.example.test.repository.RoleRepository;
import com.example.test.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Log4j2
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceRoleRepository resourceRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        memberRepository.bulkDeleteAll();
        resourceRoleRepository.bulkDeleteAll();
        roleRepository.bulkDeleteAll();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }// setUp

    @DisplayName("addRole : 권한 추가에 성공한다.")
    @WithMockUser(username = "harden", roles = "ADMIN")
    @Test
    public void addRole() throws Exception {
        // given
        Role superRole = Role.builder().parentRole(null).roleType("ROLE_ADMIN").build();

        roleRepository.save(superRole);

        RoleDTO.RequestRoleDTO requestRoleDTO = makeRequestRoleDTO(superRole.getId(), "ROLE_USER");

        // when
        ResultActions result = mockMvc.perform(
                post("/admin/role")
                        .param("parentRoleId", String.valueOf(requestRoleDTO.getParentRoleId()))
                        .param("roleType", requestRoleDTO.getRoleType())
                        .with(csrf())
        );

        // then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/roles"))
                .andDo(print());

        Role role = roleRepository.findByRoleType(requestRoleDTO.getRoleType()).get();

        assertThat(role.getRoleType()).isEqualTo(requestRoleDTO.getRoleType());
    }// addRole

    @DisplayName("addRole : 중복된 권한 추가에 실패한다.")
    @WithMockUser(username = "harden", roles = "ADMIN")
    @Test
    public void addRoleException() throws Exception {
        // given
        Role superRole = Role.builder().parentRole(null).roleType("ROLE_ADMIN").build();

        roleRepository.save(superRole);

        RoleDTO.RequestRoleDTO requestRoleDTO = makeRequestRoleDTO(superRole.getId(), "ROLE_ADMIN");

        // when
        ResultActions result = mockMvc.perform(
                post("/admin/role")
                        .param("parentRoleId", String.valueOf(requestRoleDTO.getParentRoleId()))
                        .param("roleType", requestRoleDTO.getRoleType())
                        .with(csrf())
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/error/basic"))
                .andDo(print());

    }// addRole

    @DisplayName("getRolesOrgChart : 계층형 권한을 OrgChart형태로 가져오는 데 성공한다.")
    @WithMockUser(username = "harden", roles = "ADMIN")
    @Test
    public void getRolesOrgChart() throws Exception {
        // given
        Role superRole = Role.builder().parentRole(null).roleType("ROLE_ADMIN").build();

        roleRepository.save(superRole);

        Role userRole    = Role.builder().parentRole(superRole).roleType("ROLE_USER"  ).build();
        Role managerRole = Role.builder().parentRole(superRole).roleType("ROLE_MANAGER").build();

        roleRepository.save(userRole  );
        roleRepository.save(managerRole);

        // when
        ResultActions result = mockMvc.perform(get("/admin/roles-orgchart"));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(superRole.getRoleType()))
                .andDo(print());
    }// getRolesOrgChart
    @DisplayName("updateRole : 상위 권한과 권한명을 변경하는데 성공한다.")
    @WithMockUser(username = "harden", roles = "ADMIN")
    @Test
    public void updateRole() throws Exception {
        // given
        Role superRole = Role.builder().parentRole(null).roleType("ROLE_ADMIN").build();

        roleRepository.save(superRole);

        Role managerRole = Role.builder().parentRole(superRole).roleType("ROLE_MANAGER").build();

        roleRepository.save(managerRole);

        Role userRole = Role.builder().parentRole(managerRole).roleType("ROLE_USER").build();

        roleRepository.save(userRole);

        String updateRoleType = "ROLE_UPDATE";

        // when
        ResultActions result = mockMvc.perform(
                put("/admin/role")
                        .param("roleId"      , String.valueOf(userRole.getId()))
                        .param("parentRoleId", String.valueOf(superRole.getId()))
                        .param("roleType"    , updateRoleType)
                        .with(csrf())
        );

        // then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/roles"))
                .andDo(print());

        Role changedRole = roleRepository.findById(userRole.getId()).get();

        assertThat(changedRole.getParentRole().getId()).isEqualTo(superRole.getId());
        assertThat(changedRole.getRoleType()).isEqualTo(updateRoleType);
    }// updateRole

    @DisplayName("updateRole : 변경하려는 권한 명이 이미 존재할 경우 실패한다.")
    @WithMockUser(username = "harden", roles = "ADMIN")
    @Test
    public void updateRoleException() throws Exception {
        // given
        Role superRole = Role.builder().parentRole(null).roleType("ROLE_ADMIN").build();

        roleRepository.save(superRole);

        Role userRole = Role.builder().parentRole(superRole).roleType("ROLE_USER").build();

        roleRepository.save(userRole);

        Role managerRole = Role.builder().parentRole(superRole).roleType("ROLE_MANAGER").build();

        roleRepository.save(managerRole);

        String updateRoleType = userRole.getRoleType();

        // when
        ResultActions result = mockMvc.perform(
                put("/admin/role")
                        .param("roleId"      , String.valueOf(managerRole.getId()))
                        .param("parentRoleId", String.valueOf(superRole.getId()))
                        .param("roleType"    , updateRoleType)
                        .with(csrf())
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/error/basic"))
                .andDo(print());
    }// updateRole

    public RoleDTO.RequestRoleDTO makeRequestRoleDTO(Long parentRoleId, String roleType) {
        RoleDTO.RequestRoleDTO requestRoleDTO = RoleDTO.RequestRoleDTO.builder()
                .parentRoleId(parentRoleId)
                .roleType(roleType)
                .build();

        return requestRoleDTO;
    }// makeRequestRoleDTO

}// RoleControllerTest























