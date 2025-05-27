package com.adrasha.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adrasha.core.dto.JwtUser;

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
		claims.put("roles", user.getRoles());
		claims.put("status", user.getStatus());
		
		return Jwts.builder().claims(claims).subject(user.getUsername()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
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

	public String extractRoles(String token) {
		return (String) extractClaims(token).get("roles");
	}

	public JwtUser extractJwtUser(String token) {
		
		return JwtUser.builder()
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