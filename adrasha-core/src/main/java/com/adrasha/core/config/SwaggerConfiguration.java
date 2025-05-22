package com.adrasha.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

public class SwaggerConfiguration {

	protected OpenAPI customOpenAPI() {
	    
		return new OpenAPI()
				.info(new Info().title("ADRASHA API"))				
				.addSecurityItem(new SecurityRequirement().addList("BearerAuthentication"))
				.components(new Components().addSecuritySchemes("BearerAuthentication", new SecurityScheme()
						.name("Authorization")
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")));
	}

}