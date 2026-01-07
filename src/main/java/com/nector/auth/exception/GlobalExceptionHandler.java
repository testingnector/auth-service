package com.nector.auth.exception;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

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

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<String>>  handleEmailExists(EmailAlreadyExistsException exception) {
		ApiResponse<String> response = new ApiResponse(false, "Invalid Credentials!", "CONFLICT", String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(RoleAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<String>> handleRoleExists(RoleAlreadyExistsException exception) {
		ApiResponse<String> response = new ApiResponse(false, "Invalid Credentials!", "CONFLICT", String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleRoleExists(RoleNotFoundException exception) {
		ApiResponse<String> response = new ApiResponse(false, "Role not exists in database!", "NOT FOUND", String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException exception) {
		Map<String, String> errors = new HashMap<>();
		
		exception.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		ApiResponse response = new ApiResponse(false, "Invalid credentials!", "BAD REQUEST", "400", errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	
	//----------------------Handle Login Exception---------------------------
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException exception) {
		
		ApiResponse response = new ApiResponse(false, "Invalid Email or Password!", "UNAUTHORIZED", String.valueOf(HttpStatus.UNAUTHORIZED.value()), exception.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiResponse> handleUsernameNotFound(UsernameNotFoundException exception) {
		ApiResponse response = new ApiResponse(false, "Email does not exists!", "UNAUTHORIZED", String.valueOf(HttpStatus.UNAUTHORIZED.value()), exception.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiResponse> handleAuthenticationException(AuthenticationException exception) {
		ApiResponse response = new ApiResponse(false, "Authentication Failed", "UNAUTHORIZED", String.valueOf(HttpStatus.UNAUTHORIZED.value()), exception.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
}
