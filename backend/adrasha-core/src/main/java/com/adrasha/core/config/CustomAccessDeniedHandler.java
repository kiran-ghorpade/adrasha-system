package com.adrasha.core.config;

import java.io.IOException;

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
        """, java.time.ZonedDateTime.now(), accessDeniedException.getMessage(), request.getRequestURI());

        response.getWriter().write(body);
    }
}
