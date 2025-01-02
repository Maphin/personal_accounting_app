package com.example.personal_accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.example.personal_accounting")
@EnableScheduling
public class PersonalAccountingApplication {
	public static void main(String[] args) {
		SpringApplication.run(PersonalAccountingApplication.class, args);
	}

}
