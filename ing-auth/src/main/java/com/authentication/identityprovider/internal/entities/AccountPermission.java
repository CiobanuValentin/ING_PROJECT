package com.authentication.identityprovider.internal.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "account_permission")
@Table(name = "user_permission")
public class AccountPermission {

    @EmbeddedId
    private AccountPermissionKey userPermissionId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Account account;

    @ManyToOne
    @MapsId("permissionKey")
    @JoinColumn(name = "permission_key", referencedColumnName = "permission_key", nullable = false)
    private AuthPermission authPermission;

    public AccountPermission(Account account, AuthPermission authPermission) {
        this.account = account;
        this.authPermission = authPermission;
        this.userPermissionId = new AccountPermissionKey(account.getId(), authPermission.getPermissionKey());
    }
}
