package com.farsight.huagang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ScMyConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScMyConsumerApplication.class, args);
	}

}
