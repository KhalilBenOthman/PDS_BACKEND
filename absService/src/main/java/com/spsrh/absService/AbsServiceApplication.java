package com.spsrh.absService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AbsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbsServiceApplication.class, args);
	}

}
