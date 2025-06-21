package com.adrasha.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.adrasha.core.model.Role;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfiguration implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {

        // Grab current HTTP request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Forward gateway-injected headers
            String internalSecret = request.getHeader("X-Internal-Secret");
            String userId = request.getHeader("X-UserId");
            // using system roles from here
            // String roles = request.getHeader("X-Roles");

            template.header("X-Roles", Role.SYSTEM.name());

            if(internalSecret != null) {
            	template.header("X-Internal-Secret", internalSecret);
            }
            
            if (userId != null) {
                template.header("X-UserId", userId);
            }


        }
	}
}