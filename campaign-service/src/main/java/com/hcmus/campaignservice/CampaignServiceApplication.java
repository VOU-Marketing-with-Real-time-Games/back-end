package com.hcmus.campaignservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients(basePackages = "com.campaign-service.client")
public class CampaignServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CampaignServiceApplication.class, args);
	}
}
