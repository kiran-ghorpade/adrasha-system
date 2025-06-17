package com.adrasha.apigateway.util;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adrasha.apigateway.dto.JwtUser;
import com.adrasha.apigateway.exception.JwtValidationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("uY2ZkX5h3NfFz3h8rPg7vMd0LuJqWmTk")
	private String SECRET_KEY; 

	public Claims extractClaims(String token) {
		try {
			return Jwts.parser()
					.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
					.build()
					.parseSignedClaims(token)
					.getPayload();
		}catch(Exception e) {
			e.printStackTrace();
			throw new JwtValidationException("Error while extracting claims");
		}
	}

	public Set<String> extractRoles(String token) {
		try {
			Object roles = extractClaims(token).get("roles");
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(roles, new TypeReference<Set<String>>() {});
		}catch(Exception e) {
			e.printStackTrace();
			throw new JwtValidationException("Error while converting roles");
		}
	}
	

	public JwtUser extractJwtUser(String token) {

		Claims claims = extractClaims(token);

		UUID id = UUID.fromString(claims.getSubject());
		String username = (String) claims.get("id");
		Set<String> roles = extractRoles(token);
		String status = (String) claims.get("status");

		return JwtUser.builder()
				.id(id)
				.username(username)
				.roles(roles)
				.status(status)
				.build();
	}

	public boolean isTokenValid(String token){
		try {
			Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.build()
			.parseSignedClaims(token);

			return true;
		}catch(Exception e) {
			e.printStackTrace();
			throw new JwtValidationException("Error During Parsing Jwt Token");
		}
	}
	
}