package com.authentication.identityprovider.internal.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AuthRolePermissionKey implements Serializable {

    @Column(name = "role_key")
    private String roleKey;

    @Column(name = "permission_key")
    private String permissionKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthRolePermissionKey that = (AuthRolePermissionKey) o;
        return Objects.equals(roleKey, that.roleKey) && Objects.equals(permissionKey, that.permissionKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleKey, permissionKey);
    }
}
