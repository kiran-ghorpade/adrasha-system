package com.adrasha.core.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adrasha.core.model.User;
import com.adrasha.core.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
//    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AllowedPathsProvider allowedPathsProvider;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
//        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        String path = request.getRequestURI();

        // Skip JWT check for allowed paths
        boolean isAllowed = allowedPathsProvider.getAllowedPaths().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        if (isAllowed) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get Header from request
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadCredentialsException("Missing or invalid Authorization header");
        }

        // Extract Claims from it
        String jwt = authHeader.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        String role = jwtUtil.extractRoles(jwt);

        if (username == null || !jwtUtil.isTokenValid(jwt)) {
            throw new BadCredentialsException("Invalid or expired token");
        }

        // Add Claims to request
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role);

            User userPrincipal = new User(null, username, null, role, null, null, null);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of(authority));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
