package com.adrasha.apigateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.adrasha.apigateway.util.JwtUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;


@Component
public class JwtGatewayAuthenticationFilter extends AbstractGatewayFilterFactory<JwtGatewayAuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;
    
    @Value("ThisIsMostImportantSecret")
    private String internalServiceSecret;

    public JwtGatewayAuthenticationFilter(JwtUtil jwtUtil) {
    	super(Config.class);
        this.jwtUtil = jwtUtil;
    }

	@Override
	public GatewayFilter apply(Config config) {
		 return (exchange, chain) -> {
			 	// Check Auth Headers
	            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
	            
	            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	            	return unauthorized(exchange);	
	            }

	            // Check Token
	            String token = authorizationHeader.split("Bearer ")[1];
	            
	            if (!jwtUtil.isTokenValid(token)) {
	                return unauthorized(exchange);
	            }

	            // Extract Claims and Add to Request
	            Claims claims = jwtUtil.extractClaims(token);
	            
	            Object rolesClaim = claims.get("roles");
	            
	            // extract roles and cast to list
	            ObjectMapper mapper = new ObjectMapper();
	            List<String> roles = mapper.convertValue(rolesClaim, new TypeReference<List<String>>() {});
	            
	            String rolesCSV = String.join(",", roles);
	            
	            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
	                    .header("X-Username", claims.getSubject())
	                    .header("X-Roles", rolesCSV)	
	                    .header("X-Internal-Secret", internalServiceSecret)
	                    .build();

	            // return modified exchange with updated headers
	            return chain.filter(exchange.mutate()
	            		.request(mutatedRequest)
	            		.build());
	        };
	}
    
    public static class Config {
        // Leave empty unless you want to add config properties from application.yml
    }
    
    // Helper Methods
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
    	exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    	return exchange.getResponse().setComplete();
    }
}