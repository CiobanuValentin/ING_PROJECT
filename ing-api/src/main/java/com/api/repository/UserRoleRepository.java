package com.api.repository;

import com.api.entities.UserRole;
import com.api.entities.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {
}
