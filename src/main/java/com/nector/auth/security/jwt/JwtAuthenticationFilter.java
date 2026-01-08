package com.nector.auth.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nector.auth.security.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
			
			String token = authHeader.substring(7);
			
			String username;
			
			try {
				username = jwtTokenProvider.getUsernameFromToken(token);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				
				if (jwtTokenProvider.validateToken(token, userDetails)) {
					List<String> roles = jwtTokenProvider.getRolesFromToken(token);
					
					List<GrantedAuthority> authorities = new ArrayList<>();
					for (String role : roles) {
						authorities.add(new SimpleGrantedAuthority(role.startsWith("ROLE_") ? role : "ROLE_" + role));
					}

					UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(upat);
				}
			}
			
		}
		
		
		filterChain.doFilter(request, response);
	}

}
