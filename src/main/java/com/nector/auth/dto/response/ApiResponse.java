package com.nector.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

	private boolean isSuccess;
	
	private String message;
	
	private String httpStatus;
	
	private String httpStatusCode;
	
	private T data;
	
	
}
