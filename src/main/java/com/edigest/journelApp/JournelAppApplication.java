package com.edigest.journelApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class JournelAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournelAppApplication.class, args);
	}

}
