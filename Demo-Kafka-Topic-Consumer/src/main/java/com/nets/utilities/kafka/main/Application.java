package com.nets.utilities.kafka.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.nets.utilities.kafka.*")
public class Application {
	 public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }
}
