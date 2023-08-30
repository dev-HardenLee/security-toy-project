package com.example.test.controller.admin;

import com.example.test.dto.RoleDTO;
import com.example.test.entity.Role;
import com.example.test.repository.ResourceRoleRepository;
import com.example.test.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private ResourceRoleRepository resourceRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        resourceRoleRepository.deleteAll();
        roleRepository.deleteAll();

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

        RoleDTO.RequestRoleDTO requestRoleDTO = makeRequestRoleDTO(superRole.getId());

        // when
        ResultActions result = mockMvc.perform(
                post("/admin/role")
                        .param("parentRoleId", String.valueOf(requestRoleDTO.getParentRoleId()))
                        .param("roleType", requestRoleDTO.getRoleType())
                        .with(csrf())
        );

        // then
        result.andExpect(status().is3xxRedirection()).andDo(print());

        Role role = roleRepository.findByRoleType(requestRoleDTO.getRoleType()).get();

        assertThat(role.getRoleType()).isEqualTo(requestRoleDTO.getRoleType());
    }// addRole

    @DisplayName("getRolesOrgChart : 계층형 권한을 OrgChart형태로 가져오는 데 성공한다.")
    @WithMockUser(username = "harden", roles = "ADMIN")
    @Test
    public void getRolesOrgChart() throws Exception {
        // given
        Role superRole = Role.builder().parentRole(null).roleType("ROLE_ADMIN").build();

        roleRepository.save(superRole);

        Role userRole   = Role.builder().parentRole(superRole).roleType("ROLE_USER"  ).build();
        Role systemRole = Role.builder().parentRole(superRole).roleType("ROLE_SYSTEM").build();

        roleRepository.save(userRole  );
        roleRepository.save(systemRole);

        // when
        ResultActions result = mockMvc.perform(get("/admin/roles-orgchart"));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(superRole.getRoleType()))
                .andDo(print());


    }// getRolesOrgChart

    public RoleDTO.RequestRoleDTO makeRequestRoleDTO(Long parentRoleId) {
        RoleDTO.RequestRoleDTO requestRoleDTO = RoleDTO.RequestRoleDTO.builder()
                .parentRoleId(parentRoleId)
                .roleType("ROLE_USER")
                .build();

        return requestRoleDTO;
    }// makeRequestRoleDTO

}// RoleControllerTest























