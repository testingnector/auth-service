package com.nector.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    @NotBlank(message = "Role code is required")
    @Size(max = 50, message = "Role code must be at most 50 characters")
    private String roleCode;   // unique code, e.g., "USER", "ADMIN"

    @NotBlank(message = "Role name is required")
    @Size(max = 100, message = "Role name must be at most 100 characters")
    private String roleName;   // display name

    @NotNull(message = "Company ID is required")
    private Long companyId;    // optional for multi-tenant

}
