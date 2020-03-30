/**
 * 
 */
package com.farsight.huagang.moduls.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author  liu
 *	@date  2020年3月30日
 *	@Description   
 *	@version  
 */
@Controller
public class TestController {

	@RequestMapping("/test/demo")
	@ResponseBody
	public String test() {
		return"测试成功";
	}
}
