package com.nector.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nector.auth.dto.request.RegisterRequest;
import com.nector.auth.dto.response.ApiResponse;
import com.nector.auth.dto.response.RegisterResponse;
import com.nector.auth.entity.Role;
import com.nector.auth.entity.User;
import com.nector.auth.entity.UserRole;
import com.nector.auth.exception.EmailAlreadyExistsException;
import com.nector.auth.exception.RoleNotFoundException;
import com.nector.auth.mapper.UserMapper;
import com.nector.auth.repository.RoleRepository;
import com.nector.auth.repository.UserRepository;
import com.nector.auth.repository.UserRoleRepository;
import com.nector.auth.service.AuthService;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Transactional
	@Override
	public ApiResponse registerUser(RegisterRequest registerRequest) {

		boolean exists = userRepository.existsByEmail(registerRequest.getEmail());
		System.out.println(exists);
		if (exists) {
			throw new EmailAlreadyExistsException("Email already exists!");
		}

		long userCount = userRepository.count();
		String roleCode = (userCount == 0) ? "ADMIN" : "USER";

		Role role = roleRepository.findByRoleCode(roleCode)
				.orElseThrow(() -> new RoleNotFoundException("Role not found"));

		try {
			User user = userMapper.toEntity(registerRequest);
			user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
			user.setPasswordAlgorithm("bcrypt");
			user.setIsActive(true);
			user.setCreatedAt(LocalDateTime.now());

			userRepository.save(user);

			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setCompanyId(registerRequest.getCompanyId());
			userRole.setRoleId(role.getId());
			userRole.setAssignedAt(LocalDateTime.now());
			userRole.setCreatedAt(LocalDateTime.now());

			userRoleRepository.save(userRole);

			RegisterResponse response = new RegisterResponse();
			response.setId(user.getId());
			response.setName(user.getName());
			response.setEmail(user.getEmail());
			response.setMobileNumber(user.getMobileNumber());
			response.setCompanyId(user.getCompanyId());
			response.setIsActive(user.getIsActive());

			return new ApiResponse(true, "User register successfully...", HttpStatus.CREATED,
					HttpStatus.CREATED.value(), response);

		} catch (Exception e) {
			return new ApiResponse(false, "Error occurred during user registration!", HttpStatus.INTERNAL_SERVER_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.emptyList());
		}

	}

}
