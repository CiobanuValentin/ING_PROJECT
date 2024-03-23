package com.api.entities;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import javax.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permission")
public class Permission {

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
