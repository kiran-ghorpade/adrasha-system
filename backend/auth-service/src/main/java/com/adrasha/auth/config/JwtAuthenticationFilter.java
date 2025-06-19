package com.adrasha.auth.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.adrasha.auth.dto.core.JwtUser;
import com.adrasha.auth.exception.JwtValidationException;
import com.adrasha.auth.model.Role;
import com.adrasha.auth.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AllowedPathsProvider allowedPathsProvider;

    @Value("ThisIsMostImportantSecret")
    private String internalSecret;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtUtil = jwtUtil;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean shouldStop = allowedPathsProvider.getAllowedPaths().stream()
                .anyMatch(p -> new AntPathRequestMatcher(p).matches(request));
        log.info("Jwt Filter Applied - Request " + request.getRequestURI() + " - Result " + shouldStop);
        return shouldStop;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("Processing request: " + request.getRequestURI());

        // For internal communication
        // Check for internal secret header first 
        String secret = request.getHeader("X-Internal-Secret");

        if (secret != null && internalSecret.equals(secret)) {
            log.info("Request authenticated using internal secret");

            // Setup simple authentication
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                String userId = request.getHeader("X-UserId");
                String rolesHeader = request.getHeader("X-Roles");
                List<SimpleGrantedAuthority> authorities = List.of();

                if (rolesHeader != null && !rolesHeader.isEmpty()) {
                    authorities = Arrays.stream(rolesHeader.split(","))
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                            .toList();
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
            return;
        }

        // for external communication
        // validate JWT token as before
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("AuthHeader Missing in current request: " + request.getRequestURI());
            authenticationEntryPoint.commence(request, response,
                    new BadCredentialsException("Authorization header not found with 'Bearer <token>'"));
            return;
        }

        try {
            String jwtToken = authHeader.substring(7);
            JwtUser user = jwtUtil.extractJwtUser(jwtToken);

            if (user == null || !jwtUtil.isTokenValid(jwtToken)) {
                authenticationEntryPoint.commence(request, response,
                        new BadCredentialsException("Invalid or Expired token"));
                return;
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                List<SimpleGrantedAuthority> authorities = user.getRoles()
                        .stream()
                        .map(Role::name)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                        .toList();

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

                log.info("Adding User to Security Context: " + user);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtValidationException e) {
            e.printStackTrace();
            authenticationEntryPoint.commence(request, response, new BadCredentialsException(e.getMessage()));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            authenticationEntryPoint.commence(request, response, new BadCredentialsException("Login Failed"));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
