package com.example.test.entity;

import com.example.test.converter.ResourceTypeConverter;
import com.example.test.enumeration.ResourceType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESOURCE_ID")
    private Long id;

    private String resourceName;

    private String requestMatcher;

    private String httpMethod;

    @Convert(converter = ResourceTypeConverter.class)
    private ResourceType resourceType;

}// Resource
