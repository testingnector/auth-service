package com.nector.auth.security.jwt;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.nector.auth.dto.response.ApiResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${jwt.token.validity}")
	private Long jwtTokenValidity;
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	public ApiResponse generateJwtToken(UserDetails userDetails) {

		List<String> roles = new ArrayList<>();
		
		for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
			roles.add(grantedAuthority.getAuthority());
		}
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);
		
		String token = Jwts.builder()
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
			.signWith(getKey(), SignatureAlgorithm.HS512)
			.compact();
		
		Map<String, String> data = new HashMap<>();
		data.put("token", token);
		
		return new ApiResponse(true, "Jwt token generated successfully...", HttpStatus.OK, HttpStatus.OK.value(), data);
  		
	}
	
	public SecretKey getKey() {
		byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);
		return key;
	}

}
