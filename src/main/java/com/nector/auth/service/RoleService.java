package com.nector.auth.service;

import com.nector.auth.dto.request.RoleRequest;
import com.nector.auth.dto.response.ApiResponse;

public interface RoleService {

	ApiResponse insertRole(RoleRequest roleRequest);

}
