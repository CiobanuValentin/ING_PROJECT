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
public class AccountRoleKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_key")
    private String roleKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRoleKey that = (AccountRoleKey) o;
        return userId == that.userId && Objects.equals(roleKey, that.roleKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleKey);
    }
}
