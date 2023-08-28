package com.example.test.util;

import com.example.test.dto.OrgChartDTO;
import com.example.test.entity.Role;
import com.example.test.repository.RoleRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Log4j2
class RoleOrgChartUtilTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void orgChartTest() {
        List<Role> roleList = roleRepository.findAll();

        RoleOrgChartUtil roleOrgChartUtil = new RoleOrgChartUtil(roleList);

        OrgChartDTO orgChartDTO = roleOrgChartUtil.createOrgChartDTO();

        log.info(orgChartDTO);
    }// orgChartTest

}// OrgChartUtilTest

















