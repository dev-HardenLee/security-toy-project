package com.example.test.repository;

import com.example.test.dto.ResourceSearchDTO;
import com.example.test.entity.Resource;
import com.example.test.enumeration.ResourceType;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepositoryCustom {

    public List<Resource> searchResource(ResourceSearchDTO resourceSearchDTO, Pageable pageable);

}// ResourceRepositoryCustom
