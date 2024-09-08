package com.jainejosiane.sgtarefas.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.jainejosiane.sgtarefas"})
@SpringBootApplication
public class SgtarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgtarefasApplication.class, args);
	}

}
