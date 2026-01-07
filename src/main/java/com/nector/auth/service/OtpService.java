package com.nector.auth.service;

import com.nector.auth.dto.response.ApiResponse;

public interface OtpService {

	void saveOtpInDatabase(String email, String otp);

	ApiResponse validateOtp(String email, String otp);

}
