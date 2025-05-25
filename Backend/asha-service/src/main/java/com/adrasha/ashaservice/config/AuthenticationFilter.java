package com.adrasha.ashaservice.config;

import org.springframework.stereotype.Component;

import com.adrasha.core.config.JwtAuthenticationFilter;
import com.adrasha.core.util.JwtUtil;

@Component
public class AuthenticationFilter extends JwtAuthenticationFilter {

//    public AuthenticationFilter(JwtUtil jwtUtil, JwtAuthenticationEntryPoint authenticationEntryPoint) {
//        super(jwtUtil, authenticationEntryPoint);
//    }
	

    public AuthenticationFilter(JwtUtil jwtUtil) {
        super(jwtUtil);
    }


}
