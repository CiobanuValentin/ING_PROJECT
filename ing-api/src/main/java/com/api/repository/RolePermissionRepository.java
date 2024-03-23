package com.api.repository;

import com.api.entities.Role;
import com.api.entities.RolePermission;
import com.api.entities.RolePermissionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionKey> {
}
