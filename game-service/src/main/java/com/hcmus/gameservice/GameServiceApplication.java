package com.hcmus.gameservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hcmus.gameservice.client")
public class GameServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GameServiceApplication.class, args);
	}
}
