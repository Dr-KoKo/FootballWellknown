package com.a203.sixback;

import com.a203.sixback.auth.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableScheduling
public class SixbackApplication {
//	public static final String APPLICATION_LOCATIONS = "spring.config.location="
//			+ "classpath:application.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(SixbackApplication.class)
				.run(args);
	}

}
