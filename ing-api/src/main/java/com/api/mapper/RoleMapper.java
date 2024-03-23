package com.api.mapper;

import com.api.entities.Role;
import com.api.model.RoleInput;
import com.api.output.RoleJSON;

public class RoleMapper {

    public static Role inputToRole(RoleInput input) {
        return Role.builder()
                .name(input.getName())
                .build();
    }

    public static RoleJSON roleToJson(Role role) {
        return RoleJSON.builder()
                .roleKey(role.getRoleKey())
                .name(role.getName())
                .build();
    }

}
