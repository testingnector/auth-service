package com.nector.auth.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nector.auth.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// ---------------- Email / Role exceptions ----------------
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<String>> handleEmailExists(EmailAlreadyExistsException exception) {
		
		ApiResponse<String> response = new ApiResponse<>(false, "Email already exists!", HttpStatus.CONFLICT,
				HttpStatus.CONFLICT.value(), exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(RoleAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<String>> handleRoleExists(RoleAlreadyExistsException exception) {
		
		ApiResponse<String> response = new ApiResponse<>(false, "Role already exists!", HttpStatus.CONFLICT,
				HttpStatus.CONFLICT.value(), exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleRoleNotFound(RoleNotFoundException exception) {
		
		ApiResponse<String> response = new ApiResponse<>(false, "Role not found in database!", HttpStatus.NOT_FOUND,
				HttpStatus.NOT_FOUND.value(), exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	// ---------------- Validation errors ----------------
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
			MethodArgumentNotValidException exception) {
		
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});

		ApiResponse<Map<String, String>> response = new ApiResponse<>(false, "Validation failed!",
				HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), errors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	// ---------------- Login / Authentication errors ----------------
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<String>> handleBadCredentials(BadCredentialsException exception) {
		
		ApiResponse<String> response = new ApiResponse<>(false, "Invalid Email or Password!", HttpStatus.UNAUTHORIZED,
				HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleUsernameNotFound(UsernameNotFoundException exception) {
		
		ApiResponse<String> response = new ApiResponse<>(false, "Email does not exist!", HttpStatus.UNAUTHORIZED,
				HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(javax.naming.AuthenticationException.class)
	public ResponseEntity<ApiResponse<String>> handleAuthenticationException(
			javax.naming.AuthenticationException exception) {
		
		ApiResponse<String> response = new ApiResponse<>(false, "Authentication Failed!", HttpStatus.UNAUTHORIZED,
				HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
}
