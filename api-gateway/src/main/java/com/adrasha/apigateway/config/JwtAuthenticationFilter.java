package com.adrasha.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.adrasha.apigateway.util.JwtUtil;

import io.jsonwebtoken.Claims;


@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    	super(Config.class);
        this.jwtUtil = jwtUtil;
    }

	@Override
	public GatewayFilter apply(Config config) {
		 return (exchange, chain) -> {
	            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
	            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	                return exchange.getResponse().setComplete();
	            }

	            String token = authHeader.substring(7);
	            if (!jwtUtil.isTokenValid(token)) {
	                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
	                return exchange.getResponse().setComplete();
	            }

	            Claims claims = jwtUtil.extractClaims(token);
	            exchange.getRequest().mutate()
	                    .header("X-Username", claims.getSubject())
	                    .header("X-Roles", (String) claims.get("role"))
	                    .build();

	            return chain.filter(exchange);
	        };
	}
	
    
    public static class Config {
        // Leave empty unless you want to add config properties from application.yml
    }
}