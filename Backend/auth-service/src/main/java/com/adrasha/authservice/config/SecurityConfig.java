package com.adrasha.authservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.adrasha.core.config.SecurityConfiguration;

@Configuration
@EnableWebSecurity
@Import(SecurityConfiguration.class)
public class SecurityConfig{


}
