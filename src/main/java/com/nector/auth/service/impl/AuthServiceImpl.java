package com.nector.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nector.auth.dto.request.RegisterRequest;
import com.nector.auth.entity.User;
import com.nector.auth.exception.EmailAlreadyExistsException;
import com.nector.auth.repository.UserRepository;
import com.nector.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void registerUser(RegisterRequest registerRequest) {
		
		Optional<User> existingUser = userRepository.existsByEmail(registerRequest.getEmail());
		if (existingUser.isPresent()) {
			throw new EmailAlreadyExistsException("Email already exists!");
		}
		
		User user = modelMapper.map(registerRequest, User.class);
		user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
		user.setPasswordAlgorithm("bcrypt");
		user.setMobileNumber(registerRequest.getMobileNumber());
		user.setIsActive(true);
		user.setCompanyId(registerRequest.getCompanyId());
        user.setCreatedAt(LocalDateTime.now());
        
        userRepository.save(user);
		
	}

}
