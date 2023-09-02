package com.example.test.entity;

import com.example.test.converter.ResourceTypeConverter;
import com.example.test.enumeration.ResourceType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resource")
    private List<ResourceRole> resourceRoleList = new ArrayList<>();

}// Resource
