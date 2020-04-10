package com.farsight.huagang.moduls.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	
	@RequestMapping("/403")
	public String error(ModelMap modelMap) {
		modelMap.put("template", "error/403");
		return "index";
	}
}
