package com.abakudev.retrypattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class RetryPatternApp {

	public static void main(String[] args) {
		SpringApplication.run(RetryPatternApp.class, args);
	}

}
