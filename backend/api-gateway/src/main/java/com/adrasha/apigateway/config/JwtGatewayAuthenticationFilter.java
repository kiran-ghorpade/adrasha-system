package com.adrasha.apigateway.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.adrasha.apigateway.dto.JwtUser;
import com.adrasha.apigateway.exception.JwtValidationException;
import com.adrasha.apigateway.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtGatewayAuthenticationFilter
		extends AbstractGatewayFilterFactory<JwtGatewayAuthenticationFilter.Config> {

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
	            	return unauthorized(exchange, "Authorization header not found with 'Bearer <token>'");	
	            }

	            // Check Token
	            String token = authorizationHeader.split("Bearer ")[1];
	            
	            try {
	            	if (!jwtUtil.isTokenValid(token)) {
	            		return unauthorized(exchange, "Invalid or Expired token");
	            	}

	            	// Extract Claims and Add to Request	            	
	            	JwtUser user = jwtUtil.extractJwtUser(token);

	            	Set<String> roles = user.getRoles();
	            	
	            	String rolesCSV = String.join(",", roles);

	            	ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
	            			.header("X-UserId", user.getId().toString())
	            			.header("X-Username", user.getUsername())
	            			.header("X-Roles", rolesCSV)	
	            			.header("X-Internal-Secret", internalServiceSecret)
	            			.build();

	            	// return modified exchange with updated headers
	            	return chain.filter(exchange.mutate()
	            			.request(mutatedRequest)
	            			.build());

	            } catch (JwtValidationException e) {
	            	return unauthorized(exchange, e.getMessage());
	            } catch (Exception e) {
	            	return unauthorized(exchange, "Authentication failed");
	            }
	        };
	}
    
    public static class Config {
        // Leave empty unless you want to add config properties from application.yml
    }

	// Helper Methods
	private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().add("Content-Type", "application/json");

		String timestamp = java.time.ZonedDateTime.now().toString();
		String path = exchange.getRequest().getPath().value();
		int status = HttpStatus.UNAUTHORIZED.value();
		String error = HttpStatus.UNAUTHORIZED.getReasonPhrase();
		String requestId = exchange.getRequest().getId();

		String body = String.format(
				"{ \"timestamp\": \"%s\", \"path\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"requestId\": \"%s\" }",
				timestamp, path, status, error, message, requestId);

		byte[] bytes = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
	}

}