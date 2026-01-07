package com.nector.auth.service;

import com.nector.auth.dto.request.RegisterRequest;
import com.nector.auth.dto.response.ApiResponse;

public interface AuthService {

	public ApiResponse registerUser(RegisterRequest registerRequest);

}
