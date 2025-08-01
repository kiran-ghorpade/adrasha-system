//package com.adrasha.auth.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CORSConfiguration {
//
//    @Bean
//    WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                    .allowedOriginPatterns("*")   // customize origins here
//                    .allowedMethods("*")   // GET, POST, PUT, DELETE etc
//                    .allowedHeaders("*")
//                    .allowCredentials(true);
//            }
//        };
//    }
//}
