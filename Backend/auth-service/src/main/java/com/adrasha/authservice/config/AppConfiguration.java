package com.adrasha.authservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfiguration {
	
	@Bean
    @ConditionalOnMissingBean
	ModelMapper getModelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setSkipNullEnabled(true);
		return mapper;
	}
	
	@Bean
    @ConditionalOnMissingBean
	protected PasswordEncoder passwordEncoder() {
		return  new BCryptPasswordEncoder();
	}
	
	@Bean
    @ConditionalOnMissingBean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	
}
