package com.example.test.repository;

import com.example.test.entity.Resource;
import com.example.test.entity.ResourceRole;
import com.example.test.entity.Role;
import com.example.test.enumeration.ResourceType;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    @Disabled
    @Rollback(value = false)
    void dummyInsert() {
        resourceRoleRepository.deleteAll();
        resourceRepository.deleteAll();

        Resource leaderResource = Resource.builder()
                .requestMatcher("/api/leader")
                .resourceName("Leader 정보 조회")
                .httpMethod(HttpMethod.GET.name())
                .resourceType(ResourceType.API_RESOURCE)
                .build();

        Resource systemResource = Resource.builder()
                .requestMatcher("/api/system")
                .resourceName("System 정보 조회")
                .httpMethod(HttpMethod.GET.name())
                .resourceType(ResourceType.API_RESOURCE)
                .build();

        Resource managerResource = Resource.builder()
                .requestMatcher("/api/manager")
                .resourceName("Manager 정보 조회")
                .httpMethod(HttpMethod.GET.name())
                .resourceType(ResourceType.API_RESOURCE)
                .build();

        Resource userResource = Resource.builder()
                .requestMatcher("/api/user/*")
                .resourceName("User 정보 조회")
                .httpMethod(HttpMethod.GET.name())
                .resourceType(ResourceType.API_RESOURCE)
                .build();

        Resource userPostResource = Resource.builder()
                .requestMatcher("/api/user")
                .resourceName("User 데이터 생성")
                .httpMethod(HttpMethod.POST.name())
                .resourceType(ResourceType.API_RESOURCE)
                .build();

        resourceRepository.save(leaderResource  );
        resourceRepository.save(systemResource  );
        resourceRepository.save(managerResource );
        resourceRepository.save(userResource    );
        resourceRepository.save(userPostResource);

        Role roleLeader    = roleRepository.findByRoleType("ROLE_LEADER"   ).get();
        Role roleSystem    = roleRepository.findByRoleType("ROLE_SYSTEM"   ).get();
        Role roleManager   = roleRepository.findByRoleType("ROLE_MANAGER"  ).get();
        Role roleUser      = roleRepository.findByRoleType("ROLE_USER"     ).get();
        Role roleAnonymous = roleRepository.findByRoleType("ROLE_ANONYMOUS").get();

        ResourceRole resourceRole1 = ResourceRole.builder().resource(leaderResource  ).role(roleLeader   ).build();
        ResourceRole resourceRole2 = ResourceRole.builder().resource(systemResource  ).role(roleSystem   ).build();
        ResourceRole resourceRole3 = ResourceRole.builder().resource(managerResource ).role(roleManager  ).build();
        ResourceRole resourceRole4 = ResourceRole.builder().resource(userResource    ).role(roleUser     ).build();
        ResourceRole resourceRole5 = ResourceRole.builder().resource(userPostResource).role(roleAnonymous).build();

        resourceRoleRepository.save(resourceRole1);
        resourceRoleRepository.save(resourceRole2);
        resourceRoleRepository.save(resourceRole3);
        resourceRoleRepository.save(resourceRole4);
        resourceRoleRepository.save(resourceRole5);
    }// dummyInsert

    @Test
    void findByResourceTypeWithRole() {
        List<Object[]> resourceList = resourceRepository.findByResourceTypeWithRole(ResourceType.API_RESOURCE);

        for (Object[] object : resourceList) {
            Resource resource = (Resource) object[0];
            Role     role     = (Role    ) object[2];

            log.info(resource + "  " + role);
        }// for

    }// findByResourceTypeWithRole

}// ResourceRepositoryTest


























