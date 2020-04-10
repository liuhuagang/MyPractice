/**
 * 
 */
package com.farsight.huagang.config.modules.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author  liu
 *	@date  2020年4月10日
 *	@Description   
 *	@version  
 */
@RestController
@RequestMapping("/api")
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/consumerDesc")
	public String consumerDesc() {
		return restTemplate.getForObject("http://PRODUCT/api/test", String.class) + "------------";
	}
}
