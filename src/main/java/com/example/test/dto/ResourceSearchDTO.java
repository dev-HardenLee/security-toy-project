package com.example.test.dto;

import com.example.test.enumeration.ResourceType;
import lombok.Builder;
import lombok.Data;

@Data
public class ResourceSearchDTO {

    private ResourceType resourceType;

    @Builder
    public ResourceSearchDTO(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

}// ResourceSearchDTO
