package com.adrasha.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final String[] ALLOWED_PATHS = {
			"/auth/**",
			"/v3/api-docs/**",
			"/api-docs/**",
			"/swagger-ui/**"
		};


	@Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter=null;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(ALLOWED_PATHS).permitAll()
				.anyRequest().authenticated());
		        
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}
	

//	@Autowired
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//	@Autowired
//    private final UserDetailsService userDetailsService;



}
