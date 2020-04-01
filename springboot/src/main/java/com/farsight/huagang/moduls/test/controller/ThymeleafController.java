/**
 * 
 */
package com.farsight.huagang.moduls.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author  liu
 *	@date  2020年4月1日
 *	@Description   
 *	@version  
 */
@Controller
@RequestMapping("/thy")
public class ThymeleafController {

	@RequestMapping("/test")
	public String thymeleafTest(ModelMap modelMap) {

		modelMap.addAttribute("template", "test/index");
		return "index";
	}

}
