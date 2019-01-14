package com.amarsoft.batchlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BatchlearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchlearnApplication.class, args);
	}

}

