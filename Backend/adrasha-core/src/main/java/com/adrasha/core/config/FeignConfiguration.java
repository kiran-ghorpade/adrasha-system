package com.adrasha.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfiguration implements RequestInterceptor{
	
    @Value("ThisIsImportantSecret")
    private String internalSecret;

	@Override
	public void apply(RequestTemplate template) {
		 // Add internal secret
        template.header("X-Internal-Secret", internalSecret);

        // Grab current HTTP request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Forward gateway-injected headers
            String username = request.getHeader("X-Username");
            String roles = request.getHeader("X-Roles");

            if (username != null) {
                template.header("X-Username", username);
            }

            if (roles != null) {
                template.header("X-Roles", roles);
            }
        }
	}
}