/**
 * 
 */
package com.farsight.huagang.modules.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  liu
 *	@date  2020年4月10日
 *	@Description   
 *	@version  
 */
@RestController
@RequestMapping("/api")
public class TestController {

	@RequestMapping("/test")
	public String test() {
		return "springCloudTest";
	}

}
