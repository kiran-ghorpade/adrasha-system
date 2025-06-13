package com.adrasha.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	
    	authException.printStackTrace();
    	
    	response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String body = String.format("""
        {
            "timestamp": "%s",
            "status": 403,
            "error": "Forbidden",
            "message": "%s",
            "path": "%s"
        }
        """, Instant.now(), authException.getMessage(), request.getRequestURI());

        response.getWriter().write(body);
    }
}
