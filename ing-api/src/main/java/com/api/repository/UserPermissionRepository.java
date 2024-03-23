package com.api.repository;

import com.api.entities.UserPermission;
import com.api.entities.UserPermissionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, UserPermissionKey> {
}
