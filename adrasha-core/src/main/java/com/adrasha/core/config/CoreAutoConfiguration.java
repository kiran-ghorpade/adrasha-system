package com.adrasha.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.adrasha.core")
public class CoreAutoConfiguration {
 // This will make Spring scan and register all beans in com.adrasha.core
}
