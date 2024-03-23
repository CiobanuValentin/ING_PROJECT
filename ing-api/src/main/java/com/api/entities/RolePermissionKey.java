package com.api.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolePermissionKey implements Serializable {

    @Column(name = "role_key")
    private String roleKey;

    @Column(name = "permission_key")
    private String permissionKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionKey that = (RolePermissionKey) o;
        return Objects.equals(roleKey, that.roleKey) && Objects.equals(permissionKey, that.permissionKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleKey, permissionKey);
    }
}
