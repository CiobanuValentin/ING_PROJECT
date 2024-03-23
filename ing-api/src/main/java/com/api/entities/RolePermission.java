package com.api.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role_permission")
public class RolePermission {

    @EmbeddedId
    private RolePermissionKey rolePermissionId;

    @ManyToOne
    @MapsId("roleKey")
    @JoinColumn(name = "role_key", referencedColumnName = "role_key", nullable = false)
    private Role role;

    @ManyToOne
    @MapsId("permissionKey")
    @JoinColumn(name = "permission_key", referencedColumnName = "permission_key", nullable = false)
    private Permission permission;

    public RolePermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
        this.rolePermissionId = new RolePermissionKey(role.getRoleKey(), permission.getPermissionKey());
    }
}
