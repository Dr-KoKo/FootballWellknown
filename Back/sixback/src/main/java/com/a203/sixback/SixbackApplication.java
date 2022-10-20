package com.a203.sixback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SixbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SixbackApplication.class, args);
	}

}
