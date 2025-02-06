package com.matchingMatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MatchingMatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchingMatchApplication.class, args);
	}

}
