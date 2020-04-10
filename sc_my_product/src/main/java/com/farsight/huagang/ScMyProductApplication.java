package com.farsight.huagang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScMyProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScMyProductApplication.class, args);
	}

}
