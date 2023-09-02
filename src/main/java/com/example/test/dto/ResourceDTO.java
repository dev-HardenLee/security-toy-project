package com.example.test.dto;

import com.example.test.converter.ResourceTypeConverter;
import com.example.test.enumeration.ResourceType;
import lombok.Data;

import javax.persistence.Convert;
import java.util.List;

@Data
public class ResourceDTO {

    private Long id;

    private String resourceName;

    private String requestMatcher;

    private String httpMethod;

    private ResourceType resourceType;

    private List<RoleDTO> roleDTOList;

}// ResourceDTO
