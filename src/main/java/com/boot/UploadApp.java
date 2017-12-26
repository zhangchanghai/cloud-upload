package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class UploadApp {

	public static void main(String[] args) {
		SpringApplication.run(UploadApp.class, args);
		System.out.println("====cloud-upload 启动完毕====");
	}

}
