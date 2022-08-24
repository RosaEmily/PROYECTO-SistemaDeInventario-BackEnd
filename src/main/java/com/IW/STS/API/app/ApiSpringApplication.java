package com.IW.STS.API.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApiSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSpringApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8080/").allowedMethods(CorsConfiguration.ALL).allowedHeaders(CorsConfiguration.ALL);
				/*
				registry.addMapping("/**").allowedOrigins("http://44.192.43.78/").allowedMethods(CorsConfiguration.ALL).allowedHeaders(CorsConfiguration.ALL);
				*/
			}
		};
	}	
	 
}
