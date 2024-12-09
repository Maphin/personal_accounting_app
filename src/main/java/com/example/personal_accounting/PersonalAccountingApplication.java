package com.example.personal_accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.personal_accounting")
public class PersonalAccountingApplication {
	public static void main(String[] args) {
		SpringApplication.run(PersonalAccountingApplication.class, args);
	}

}
