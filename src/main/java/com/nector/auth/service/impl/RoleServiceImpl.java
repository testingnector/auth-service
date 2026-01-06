package com.nector.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nector.auth.dto.request.RoleRequest;
import com.nector.auth.dto.response.ApiResponse;
import com.nector.auth.entity.Role;
import com.nector.auth.exception.RoleAlreadyExistsException;
import com.nector.auth.repository.RoleRepository;
import com.nector.auth.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    
    @Override
    public ApiResponse insertRole(RoleRequest roleRequest) {

//    	--------------------------------------------------------------------------
        // Check if role already exists for company
    	
    	try {
    		roleRepository.findByRoleCodeAndCompanyId(roleRequest.getRoleCode(), roleRequest.getCompanyId())
    		.ifPresent(r -> {
    			throw new RoleAlreadyExistsException("Role code already exists for this company");
    		});
    		
    		Role role = modelMapper.map(roleRequest, Role.class);
    		role.setIsActive(true);
    		role.setCreatedAt(LocalDateTime.now());
    		
    		Role saveRole = roleRepository.save(role);
    		return new ApiResponse(true, "Role inserted successfully...", saveRole);
			
		} catch (Exception e) {
			return new ApiResponse(false, "Error occurred during role insertion!", Collections.emptyList());

		}
    }
    
    
//	--------------------------------------------------------------------------


}
