package com.example.test.repository;

import com.example.test.dto.ResourceSearchDTO;
import com.example.test.entity.QResource;
import com.example.test.entity.QResourceRole;
import com.example.test.entity.QRole;
import com.example.test.entity.Resource;
import com.example.test.enumeration.ResourceType;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.querydsl.jpa.JPAExpressions.selectDistinct;

@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Resource> searchResource(ResourceSearchDTO resourceSearchDTO, Pageable pageable) {

        QResource     resource     = QResource.resource;
        QResourceRole resourceRole = QResourceRole.resourceRole;
        QRole         role         = QRole.role;

        List<Resource> resultList = queryFactory
                .selectFrom(resource).distinct()
                .leftJoin(resource.resourceRoleList, resourceRole).fetchJoin()
                .leftJoin(resourceRole.role, role).fetchJoin()
                .where(resourceTypeEq(resourceSearchDTO))
                .fetch();

        return resultList;
    }// searchResource

    private Predicate resourceTypeEq(ResourceSearchDTO resourceSearchDTO) {
        if(resourceSearchDTO.getResourceType() == null) return null;

        ResourceType resourceType = resourceSearchDTO.getResourceType();

        return QResource.resource.resourceType.eq(resourceType);
    }// resourceTypeEq

}// ResourceRepositoryCustomImpl
