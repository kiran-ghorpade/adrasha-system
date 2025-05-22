package com.adrasha.ncd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.adrasha.core.config.SecurityConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfiguration{


}
