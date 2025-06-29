package com.adrasha.auth.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.exception.JwtValidationException;
import com.adrasha.auth.model.AccountStatus;
import com.adrasha.auth.model.Role;
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
		try {
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", user.getUsername());
			claims.put("status", user.getStatus());

			claims.put("roles", user.getRoles());

			return Jwts.builder()
					.claims(claims)
					.subject(user.getId().toString())
					.issuedAt(new Date())
					.expiration(new Date(System.currentTimeMillis() + expiration))
					.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
					.compact();

		}catch(Exception e) {
			e.printStackTrace();
			throw new JwtValidationException("Error While generating JWT Token");
		}
	}

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

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public Set<Role> extractRoles(String token) {
		try {
			Object roles = extractClaims(token).get("roles");
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(roles, new TypeReference<Set<Role>>() {});
		}catch(Exception e) {
			e.printStackTrace();
			throw new JwtValidationException("Error while converting roles");
		}
	}

	public JwtUser extractJwtUser(String token) {

		Claims claims = extractClaims(token);

		UUID id = UUID.fromString(claims.getSubject());
		String username = (String) claims.get("id");
		Set<Role> roles = extractRoles(token);
		AccountStatus status = AccountStatus.valueOf((String) claims.get("status"));

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

	public long getExpiration() {

		return this.expiration;
	}
}