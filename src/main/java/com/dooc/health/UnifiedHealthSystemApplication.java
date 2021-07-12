package com.dooc.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class UnifiedHealthSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnifiedHealthSystemApplication.class, args);
	}

}
