package com.IW.STS.API.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApiSpringApplication {
	
	@Value("${apispring.app.IP}")
	private String IP;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiSpringApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://"+IP).allowedMethods(CorsConfiguration.ALL).allowedHeaders(CorsConfiguration.ALL);			
			}
		};
	}	
	 
}
