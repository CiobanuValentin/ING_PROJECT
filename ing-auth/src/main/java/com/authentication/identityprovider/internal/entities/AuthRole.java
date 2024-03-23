package com.authentication.identityprovider.internal.entities;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "description")
    private String description;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "subject", nullable = false)
    private String subject;


    @PrePersist
    private void generateId() {
        this.setRoleKey(NanoIdUtils.randomNanoId());
    }
}
