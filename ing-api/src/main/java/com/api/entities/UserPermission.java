package com.api.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_permission")
public class UserPermission {

    @EmbeddedId
    private UserPermissionKey userPermissionId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("permissionKey")
    @JoinColumn(name = "permission_key", referencedColumnName = "permission_key", nullable = false)
    private Permission permission;

    public UserPermission(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
        this.userPermissionId = new UserPermissionKey(user.getId(), permission.getPermissionKey());
    }
}
