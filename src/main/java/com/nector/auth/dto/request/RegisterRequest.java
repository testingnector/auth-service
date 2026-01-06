package com.nector.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	
	@NotBlank(message = "Company id is required!")
	private Long companyId;
}
