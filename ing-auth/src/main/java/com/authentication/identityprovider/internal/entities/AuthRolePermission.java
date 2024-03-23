package com.authentication.identityprovider.internal.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role_permission")
public class AuthRolePermission {

    @EmbeddedId
    private AuthRolePermissionKey rolePermissionId;

    @ManyToOne
    @MapsId("roleKey")
    @JoinColumn(name = "role_key", referencedColumnName = "role_key", nullable = false)
    private AuthRole role;

    @ManyToOne
    @MapsId("permissionKey")
    @JoinColumn(name = "permission_key", referencedColumnName = "permission_key", nullable = false)
    private AuthPermission permission;

    public AuthRolePermission(AuthRole role, AuthPermission permission) {
        this.role = role;
        this.permission = permission;
        this.rolePermissionId = new AuthRolePermissionKey(role.getRoleKey(), permission.getPermissionKey());
    }
}
