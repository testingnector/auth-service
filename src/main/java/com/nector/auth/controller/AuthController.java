package com.nector.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nector.auth.dto.request.LoginRequest;
import com.nector.auth.dto.request.RegisterRequest;
import com.nector.auth.dto.request.VerifyOtpRequest;
import com.nector.auth.dto.response.ApiResponse;
import com.nector.auth.dto.response.RegisterResponse;
import com.nector.auth.entity.User;
import com.nector.auth.security.CustomUserDetailsService;
import com.nector.auth.security.jwt.JwtTokenProvider;
import com.nector.auth.service.AuthService;
import com.nector.auth.service.EmailService;
import com.nector.auth.service.OtpService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
		
		ApiResponse response = authService.registerUser(registerRequest);
		if (response.isSuccess()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(email, password);
 		Authentication authentication = authenticationManager.authenticate(upat);
 		
 		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
 		
 		String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
 		
 		otpService.saveOtpInDatabase(email, otp);
 		
 		ApiResponse response = emailService.sendOtpToEmail(email, otp);
 		
 		if (response.isSuccess()) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
 		else {
 			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
 		
	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {

		ApiResponse response = otpService.validateOtp(verifyOtpRequest.getEmail(), verifyOtpRequest.getOtp());
		if (!response.isSuccess()) {
			return ResponseEntity.status(response.getHttpStatus()).body(response);
		}
		
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(verifyOtpRequest.getEmail());
		ApiResponse jwtResponse = jwtTokenProvider.generateJwtToken(userDetails);
		
		return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
	}
	
	
	
}
