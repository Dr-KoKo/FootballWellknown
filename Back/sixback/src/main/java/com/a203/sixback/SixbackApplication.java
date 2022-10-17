package com.a203.sixback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SixbackApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.properties,"
			+"classpath:application-key.properties";
	public static void main(String[] args) {
		new SpringApplicationBuilder(SixbackApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
