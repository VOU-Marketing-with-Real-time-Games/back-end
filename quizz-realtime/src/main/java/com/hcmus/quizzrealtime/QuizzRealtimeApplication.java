package com.hcmus.quizzrealtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hcmus.quizzrealtime.client")
public class QuizzRealtimeApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuizzRealtimeApplication.class, args);
	}
}
