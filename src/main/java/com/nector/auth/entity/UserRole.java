package com.nector.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long userId;   // reference to User service
    private Long roleId;   // reference to Role service
    private Long companyId;

    private Boolean isActive = true;

    private LocalDateTime assignedAt;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}
