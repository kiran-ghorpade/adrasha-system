package com.adrasha.authservice.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adrasha.authservice.dto.JwtUser;
import com.adrasha.authservice.model.AccountStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("uY2ZkX5h3NfFz3h8rPg7vMd0LuJqWmTk")
	private String SECRET_KEY; 
	
	@Value("86400000")
	private long expiration; // a day in milliseconds

	public String generateToken(JwtUser user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("status", user.getStatus());
		claims.put("roles", user.getRoles());
		
		return Jwts.builder()
				.claims(claims)
				.subject(user.getUsername())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.compact();
	}

	public Claims extractClaims(String token) {
		return Jwts.parser()
				.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public List<String> extractRoles(String token) {
		Object roles = extractClaims(token).get("roles");
        ObjectMapper mapper = new ObjectMapper();
        
        List<String> rolesList = Optional.ofNullable(roles)
        		.map(r-> mapper.convertValue(r, new TypeReference<List<String>>() {}))
        		.orElse(List.of());
        
        return rolesList;
	}

	public JwtUser extractJwtUser(String token) {
		
		Claims claims = extractClaims(token);
		
		String username = claims.getSubject();
		UUID id = UUID.fromString((String) claims.get("id"));
		List<String> roles = extractRoles(token);
		AccountStatus status = AccountStatus.valueOf((String) claims.get("status"));
		
		return JwtUser.builder()
				.id(id)
				.username(username)
				.roles(roles)
				.status(status)
				.build();
		}
	
	public boolean isTokenValid(String token) {
			Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.build()
			.parseSignedClaims(token);
			
			return true;
	}

	public long getExpiration() {

		return this.expiration;
	}
}