package com.api.mapper;

import com.api.entities.Permission;
import com.api.model.PermissionInput;
import com.api.output.PermissionJSON;

public class PermissionMapper {

    public static Permission inputToPermission(PermissionInput input) {
        return Permission.builder()
                .subject(input.getSubject())
                .action(input.getAction())
                .description(input.getDescription())
                .build();
    }

    public static PermissionJSON permissionToJson(Permission permission) {
        return PermissionJSON.builder()
                .permissionKey(permission.getPermissionKey())
                .subject(permission.getSubject())
                .action(permission.getAction())
                .description(permission.getDescription())
                .build();
    }

}
