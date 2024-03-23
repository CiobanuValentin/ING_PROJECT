package com.api.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {

    @EmbeddedId
    private UserRoleKey userRoleId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("roleKey")
    @JoinColumn(name = "role_key", referencedColumnName = "role_key", nullable = false)
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.userRoleId = new UserRoleKey(user.getId(), role.getRoleKey());
    }
}
