package com.example.test.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = {"resource", "role"})
@Getter
@Builder
public class ResourceRole extends BaseEntity {

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

    public void createRelationShip(Resource resource) {
        if(this.resource == null) {
            this.resource = resource;
            this.resource.getResourceRoleList().add(this);
        }// if
    }// createRelationShip

    public void breakRelationShip() {
        if(this.resource != null && this.resource.getResourceRoleList().contains(resource)) {
            this.resource.getResourceRoleList().remove(resource);
        }// if
    }// breakRelationShip

}// ResourceRole
















