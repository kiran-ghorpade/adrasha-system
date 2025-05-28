package com.adrasha.authservice.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adrasha.authservice.dto.JwtUser;
import com.adrasha.authservice.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
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
    protected boolean shouldNotFilter(HttpServletRequest request) {
    	
        boolean shouldStop = allowedPathsProvider.getAllowedPaths().stream()
            .anyMatch(p -> new AntPathRequestMatcher(p).matches(request));
        
        log.info("Jwt Filter Applied - Request " + request.getRequestURI() + " - Result "+shouldStop);
        
        return shouldStop;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
		log.info("Allowed Path request : "+ request);

        // Get Header from request
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			log.info("AuthHeader Missing in current request : "+ request);
            throw new BadCredentialsException("Missing or invalid Authorization header");
        }

        // Extract Claims from it
        String jwtToken = authHeader.substring(7);
        JwtUser user = jwtUtil.extractJwtUser(jwtToken);

        if (user == null || !jwtUtil.isTokenValid(jwtToken)) {
        	log.info("Invalid JWT Token : " + jwtToken);
            throw new BadCredentialsException("Invalid or expired token");
        }
                
        // Add Claims to request
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = user.getRoles()
            								.stream()
            								.map(SimpleGrantedAuthority::new)
            								.toList();

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

            log.info("Adding User to Security Context : " + user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
