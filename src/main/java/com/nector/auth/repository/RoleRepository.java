package com.nector.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nector.auth.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{

    Optional<Role> findByRoleCodeAndCompanyId(String roleCode, Long companyId);

}
