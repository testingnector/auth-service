package com.nector.auth.service;

public interface OtpService {

	void saveOtpInDatabase(String email, String otp);

}
