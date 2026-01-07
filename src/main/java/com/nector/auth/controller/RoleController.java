package com.nector.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nector.auth.dto.request.RoleRequest;
import com.nector.auth.dto.response.ApiResponse;
import com.nector.auth.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse> insertRole(@Valid @RequestBody RoleRequest roleRequest) {
        ApiResponse response = roleService.insertRole(roleRequest);
        if (response.isSuccess()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
        else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
    }
}
