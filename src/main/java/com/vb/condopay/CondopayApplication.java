package com.vb.condopay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CondopayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CondopayApplication.class, args);
	}

}
