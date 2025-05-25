package com.adrasha.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private AllowedPathsProvider allowedPathsProvider;

    @Bean
    @ConditionalOnMissingBean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     	
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                		.requestMatchers(
                			    allowedPathsProvider.getAllowedPaths()
                			        .stream()
                			        .map(AntPathRequestMatcher::new)
                			        .toArray(RequestMatcher[]::new)
                			).permitAll())
            	.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            	.exceptionHandling((exceptionHandling) ->
                 		exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                 );
 
        return http.build();
    }
}
