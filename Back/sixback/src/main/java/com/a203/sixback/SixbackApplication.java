package com.a203.sixback;

import com.a203.sixback.auth.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SixbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SixbackApplication.class, args);
	}

}
