package com.adrasha.masterdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig{
	
	@Value("http://localhost:8080")
	private String swaggerGatewayURL;
	
	@Value("${spring.application.name}")
	private String applicationName;

	@Bean
	protected OpenAPI customOpenAPI() {
	    
		return new OpenAPI()
		        .addServersItem(new Server().url(swaggerGatewayURL))
				.info(new Info().title("ADRASHA "+applicationName.toUpperCase()+" API Docs"))				
				.addSecurityItem(new SecurityRequirement().addList("BearerAuthentication"))
				.components(new Components().addSecuritySchemes("BearerAuthentication", new SecurityScheme()
						.name("Authorization")
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")));
	}

}