package com.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString(exclude = {"resource", "role"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESOURCE_ROLE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESOURCE_ID")
    private Resource resource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

}// ResourceRole
















