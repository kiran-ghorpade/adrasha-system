package com.adrasha.core.config;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AllowedPathsProvider {
	
    public List<String> getAllowedPaths() {
        return List.of(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api-docs/**",
            "/auth/**"
        );
    }
}