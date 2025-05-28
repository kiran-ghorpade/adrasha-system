package com.adrasha.core.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
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
    
    @Value("ThisIsImportantSecret")
    private String internalSecret;
    
    @Autowired
    private AllowedPathsProvider allowedPathsProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return allowedPathsProvider.getAllowedPaths().stream()
            .anyMatch(p -> new AntPathRequestMatcher(p).matches(request));
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String secrete = request.getHeader("X-Internal-Secret");

        if (!internalSecret.equals(secrete)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("Forbidden: Invalid or missing X-Internal-Secret");
            return;
        }

        // Get Header from request
        final String username = request.getHeader("X-Username");
        final String roles = request.getHeader("X-Roles");

        if (username == null || roles == null) {
            throw new BadCredentialsException("Missing or invalid Authorization header");
        }

        // Add Claims to request
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
        	
            List<SimpleGrantedAuthority> authorities =  Arrays.stream(roles.split(","))
            								.map(SimpleGrantedAuthority::new)
            								.toList();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
