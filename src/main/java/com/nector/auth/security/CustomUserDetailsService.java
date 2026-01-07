package com.nector.auth.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nector.auth.entity.Role;
import com.nector.auth.entity.User;
import com.nector.auth.entity.UserRole;
import com.nector.auth.repository.RoleRepository;
import com.nector.auth.repository.UserRepository;
import com.nector.auth.repository.UserRoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("Username is not found!"));

	    List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
	    List<String> roles = new ArrayList<>();

	    for (UserRole ur : userRoles) {
	        Optional<Role> roleOpt = roleRepository.findById(ur.getRoleId());
	        if (roleOpt.isPresent()) {
	            roles.add("ROLE_" + roleOpt.get().getRoleCode());
	        } else {
	            roles.add("ROLE_USER"); // default role
	        }
	    }


	    return org.springframework.security.core.userdetails.User
	            .withUsername(user.getEmail())
	            .password(user.getPasswordHash()) // hashed password
	            .authorities(roles.toArray(new String[0]))
	            .accountExpired(false)
	            .accountLocked(false)
	            .credentialsExpired(false)
	            .disabled(!user.getIsActive())
	            .build();
	}

}
