package com.adrasha.auth.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
    	accessDeniedException.printStackTrace();
    	
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
        """, Instant.now(), accessDeniedException.getMessage(), request.getRequestURI());

        response.getWriter().write(body);
    }
}
