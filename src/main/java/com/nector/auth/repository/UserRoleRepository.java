package com.nector.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nector.auth.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID>{

}
