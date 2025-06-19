package com.adrasha.analytics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.adrasha.core.config.AppConfiguration;

@Configuration
@Import(AppConfiguration.class)
public class AppConfig{
	
}
