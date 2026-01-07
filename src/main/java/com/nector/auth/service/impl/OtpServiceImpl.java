package com.nector.auth.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nector.auth.entity.OtpVerification;
import com.nector.auth.repository.OtpRepository;
import com.nector.auth.service.OtpService;

import jakarta.transaction.Transactional;

@Service
public class OtpServiceImpl implements OtpService{

	
	@Autowired
	private OtpRepository otpRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${otp.expiry.minutes}")
	private Long otpExpiryMinutes;

	
	@Override
	@Transactional
	public void saveOtpInDatabase(String email, String otp) {

		OtpVerification otpEntity = otpRepository.findByEmail(email).orElse(new OtpVerification());
		
		if (otpEntity.getEmail() == null) {
			otpEntity.setEmail(email);
		}
		
		otpEntity.setOtp(passwordEncoder.encode(otp));
		otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(otpExpiryMinutes));
		otpEntity.setUsed(false);
		
		otpRepository.save(otpEntity);
	}

	
}
