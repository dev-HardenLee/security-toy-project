package com.example.test.repository;

import com.example.test.dto.ResourceSearchDTO;
import com.example.test.entity.Resource;
import com.example.test.entity.ResourceRole;
import com.example.test.entity.Role;
import com.example.test.enumeration.ResourceType;
import com.querydsl.core.Tuple;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Log4j2
class ResourceRepositoryTest {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceRoleRepository resourceRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByResourceTypeWithRole() {
        List<Resource> resourceList = resourceRepository.findByResourceTypeWithRole(ResourceType.API_RESOURCE);

        log.info(resourceList.size());

        for (Resource resource : resourceList) {

            log.info(resource);

            for (ResourceRole resourceRole : resource.getResourceRoleList()) {
                log.info("  " + resourceRole.getRole());
            }// for
        }// for

    }// findByResourceTypeWithRole

    @Test
    @DisplayName("searchResource : 쿼리 조회에 성공한다.")
    public void searchResource() {
        // given
        ResourceSearchDTO searchDTO = ResourceSearchDTO.builder().resourceType(ResourceType.API_RESOURCE).build();
        Pageable pageRequest = PageRequest.of(0, 10);

        // when
        List<Resource> resultList = resourceRepository.searchResource(searchDTO, pageRequest);

        // then
        for (Resource resource: resultList) {
            log.info(resource + " " + resource.getResourceRoleList());
        }// for

    }// searchResource

}// ResourceRepositoryTest


























