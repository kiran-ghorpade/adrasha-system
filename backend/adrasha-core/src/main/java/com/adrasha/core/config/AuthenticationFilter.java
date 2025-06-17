package com.adrasha.core.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    
    @Value("ThisIsMostImportantSecret")
    private String internalSecret;
    
    @Autowired
    private AllowedPathsProvider allowedPathsProvider;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return allowedPathsProvider.getAllowedPaths().stream()
            .anyMatch(p -> new AntPathRequestMatcher(p).matches(request));
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String secret = request.getHeader("X-Internal-Secret");

        if (!internalSecret.equals(secret)) {
        	accessDeniedHandler.handle(request, response, new AccessDeniedException("Invalid or missing X-Internal-Secret "));
            return;
        }

        // Get Header from request
        final String userId = request.getHeader("X-UserId");
        final String roles = request.getHeader("X-Roles");

        if (userId == null || roles == null) {
            accessDeniedHandler.handle(request, response, new AccessDeniedException("Missing or invalid Authorization header"));
            return;
        }

        // Add Claims to request
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
        	
            List<SimpleGrantedAuthority> authorities =  Arrays.stream(roles.split(","))
            								.map(role -> new SimpleGrantedAuthority("ROLE_"+ role.trim()))
            								.toList();

            // principle is userId , if principal changes, please update code in all services
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
