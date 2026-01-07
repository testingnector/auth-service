package com.nector.auth.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotBlank(message = "Name is required!")
	private String name;

	@NotBlank(message = "Email is required!")
	@Email(message = "Invalid email format!")
	private String email;
	
	@NotBlank(message = "Password is required!")
	private String password;
	
	@NotBlank(message = "Mobile number is required!")
	private String mobileNumber;
	
	@NotNull(message = "Company id is required!")
	private UUID companyId;
}
