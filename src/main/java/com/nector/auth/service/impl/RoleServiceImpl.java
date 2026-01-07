package com.nector.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nector.auth.dto.request.RoleRequest;
import com.nector.auth.dto.response.ApiResponse;
import com.nector.auth.dto.response.RoleResponse;
import com.nector.auth.entity.Role;
import com.nector.auth.exception.RoleAlreadyExistsException;
import com.nector.auth.mapper.RoleMapper;
import com.nector.auth.repository.RoleRepository;
import com.nector.auth.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public ApiResponse insertRole(RoleRequest roleRequest) {

//    	--------------------------------------------------------------------------
		// Check if role already exists for company

		roleRepository.findByRoleCodeAndCompanyId(roleRequest.getRoleCode(), roleRequest.getCompanyId())
				.ifPresent(r -> {
					throw new RoleAlreadyExistsException("Role code already exists for this company");
				});
		try {
			Role role = roleMapper.toEntity(roleRequest);
			role.setIsActive(true);
			role.setCreatedAt(LocalDateTime.now());

			Role saveRole = roleRepository.save(role);

			RoleResponse response = new RoleResponse();
			response.setId(saveRole.getId());
			response.setRoleCode(saveRole.getRoleCode());
			response.setRoleName(saveRole.getRoleName());
			response.setCompanyId(saveRole.getCompanyId());
			response.setIsActive(saveRole.getIsActive());

			return new ApiResponse(true, "Role inserted successfully...", HttpStatus.CREATED,
					HttpStatus.CREATED.value(), response);

		} catch (Exception e) {
			return new ApiResponse(false, "Error occurred during role insertion!", HttpStatus.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.emptyList());

		}
	}

//	--------------------------------------------------------------------------

}
