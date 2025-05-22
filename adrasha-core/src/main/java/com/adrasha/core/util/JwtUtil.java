package com.adrasha.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String SECRET_KEY; 
	
	@Value("${jwt.expiration}")
	private long expiration; 

	public String generateToken(String username, String roles) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);
		return Jwts.builder().claims(claims).subject(username).issuedAt(new Date())
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

	public boolean isTokenValid(String token) {
			Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.build()
			.parseSignedClaims(token);
			
			return true;
	}
}