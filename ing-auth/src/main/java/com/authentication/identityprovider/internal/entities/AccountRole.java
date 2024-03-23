package com.authentication.identityprovider.internal.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "account_role")
@Table(name = "user_role")
public class AccountRole {

    @EmbeddedId
    private AccountRoleKey userRoleId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Account account;

    @ManyToOne
    @MapsId("roleKey")
    @JoinColumn(name = "role_key", referencedColumnName = "role_key", nullable = false)
    private AuthRole authRole;

    public AccountRole(Account account, AuthRole authRole) {
        this.account = account;
        this.authRole = authRole;
        this.userRoleId = new AccountRoleKey(account.getId(), authRole.getRoleKey());
    }
}
