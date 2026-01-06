package com.nector.auth.service;

import com.nector.auth.dto.request.RegisterRequest;

public interface AuthService {

	public void registerUser(RegisterRequest registerRequest);

}
