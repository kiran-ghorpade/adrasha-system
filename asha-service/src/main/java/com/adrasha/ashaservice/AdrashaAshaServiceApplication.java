package com.adrasha.ashaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.adrasha.core")
@EnableFeignClients(basePackages = "com.adrasha.core.service")
public class AdrashaAshaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdrashaAshaServiceApplication.class, args);
	}

}