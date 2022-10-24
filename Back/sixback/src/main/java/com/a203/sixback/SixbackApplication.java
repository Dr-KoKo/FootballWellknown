package com.a203.sixback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.a203.sixback.db.repo")
@EnableMongoRepositories(basePackages = "com.a203.sixback.db.mongo")
public class SixbackApplication {
	public static void main(String[] args) {
		SpringApplication.run(SixbackApplication.class, args);
	}

}
