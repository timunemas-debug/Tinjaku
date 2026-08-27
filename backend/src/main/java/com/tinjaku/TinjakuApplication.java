package com.tinjaku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TinjakuApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinjakuApplication.class, args);
	}
}