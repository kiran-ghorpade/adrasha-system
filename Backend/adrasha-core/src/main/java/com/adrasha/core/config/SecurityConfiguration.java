package com.adrasha.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	private AuthenticationFilter authenticationFilter;

	@Autowired
	private AllowedPathsProvider allowedPathsProvider;

	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;

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
						).permitAll()
				.anyRequest().authenticated())
		.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling((exceptionHandling) ->
		exceptionHandling.accessDeniedHandler(accessDeniedHandler)
				);



		return http.build();
	}
}
