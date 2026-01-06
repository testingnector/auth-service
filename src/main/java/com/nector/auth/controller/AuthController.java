package com.nector.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nector.auth.dto.request.RegisterRequest;
import com.nector.auth.entity.User;
import com.nector.auth.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/user/insert")
	public String register(@RequestBody RegisterRequest registerRequest) {
		
		authService.registerUser(registerRequest);
		
		return null;
	}
	
}
