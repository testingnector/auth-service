package com.nector.auth.dto.response;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean isSuccess;

    private String message;

    private HttpStatus httpStatus;   // change from String to HttpStatus

    private int httpStatusCode;      // change from String to int

    private T data;
}
