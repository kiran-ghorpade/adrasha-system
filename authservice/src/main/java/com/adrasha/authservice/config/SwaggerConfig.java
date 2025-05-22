package com.adrasha.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adrasha.core.config.SwaggerConfiguration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig extends SwaggerConfiguration{

	@Bean
	protected OpenAPI customOpenAPI() {
	    
		return new OpenAPI()
				.info(new Info().title("ADRASHA Authentication Service"))				
				.addSecurityItem(new SecurityRequirement().addList("BearerAuthentication"))
				.components(new Components().addSecuritySchemes("BearerAuthentication", new SecurityScheme()
						.name("Authorization")
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")));
	}

}