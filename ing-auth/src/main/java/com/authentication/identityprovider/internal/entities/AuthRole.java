package com.authentication.identityprovider.internal.entities;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "auth_role")
@Table(name = "role")
public class AuthRole {

    @Id
    @Column(name = "role_key", nullable = false, unique = true)
    private String roleKey;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.PERSIST)
    private List<AuthRolePermission> rolePermissionList;

    @PrePersist
    private void generateId() {
        this.setRoleKey(NanoIdUtils.randomNanoId());
    }
}
