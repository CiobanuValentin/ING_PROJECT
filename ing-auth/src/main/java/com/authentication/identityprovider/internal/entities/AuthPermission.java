package com.authentication.identityprovider.internal.entities;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "auth_permission")
@Table(name = "permission")
public class AuthPermission {

    @Id
    @Column(name = "permission_key", nullable = false, unique = true)
    private String permissionKey;

    @Column(name = "description")
    private String description;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "subject", nullable = false)
    private String subject;


    @PrePersist
    private void generateId() {
        this.setPermissionKey(NanoIdUtils.randomNanoId());
    }
}
