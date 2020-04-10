/**
 * 
 */
package com.farsight.huagang.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author  liu
 *	@date  2020年4月10日
 *	@Description   
 *	@version  
 */
@Configuration
public class consumerConfig {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate () {
		return new RestTemplate();
	}
	
}
