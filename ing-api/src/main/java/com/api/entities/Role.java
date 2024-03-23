package com.api.entities;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import javax.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "role_key", nullable = false, unique = true)
    private String roleKey;

    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "role", cascade = CascadeType.PERSIST)
    private List<RolePermission> rolePermissionList;

    @PrePersist
    private void generateId() {
        this.setRoleKey(NanoIdUtils.randomNanoId());
    }

}
