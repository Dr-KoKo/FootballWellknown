package com.a203.sixback;

import com.a203.sixback.auth.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.a203.sixback.db.repo")
@EnableMongoRepositories(basePackages = "com.a203.sixback.db.mongo")
public class SixbackApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(SixbackApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
