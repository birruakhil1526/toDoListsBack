package com.niit.APIGateWayApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGateWayAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGateWayAppApplication.class, args);
	}
}
