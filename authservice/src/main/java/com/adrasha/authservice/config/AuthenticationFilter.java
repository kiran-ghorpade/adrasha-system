package com.adrasha.authservice.config;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.adrasha.core.config.JwtAuthenticationFilter;
import com.adrasha.core.util.JwtUtil;

@Component
public class AuthenticationFilter extends  JwtAuthenticationFilter{

	public AuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		super(jwtUtil, userDetailsService);
		// TODO Auto-generated constructor stub
	}

  
}
