/**
 * 
 */
package com.farsight.huagang.moduls.test.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.farsight.huagang.moduls.test.entity.City;
import com.farsight.huagang.moduls.test.entity.Country;
import com.farsight.huagang.moduls.test.service.CountryService;

/**
 * @author  liu
 *	@date  2020年4月1日
 *	@Description   
 *	@version  
 */
@Controller
@RequestMapping("/thy")
public class ThymeleafController {
	@Autowired
	private CountryService cS;

	@RequestMapping("/test")
	public String thymeleafTest(ModelMap modelMap) {
		int countryId = 522;
		//先将list转换成流 然后限制数量 再转换成list
		List<City> cities = cS.getCitiesByCountryId(countryId).stream().limit(10)
				.collect(Collectors.toList());
		Country country = cS.getCountryById(countryId);

		modelMap.addAttribute("thymeleafTitle", "Spring thymeleaf test page");
		modelMap.addAttribute("checked", true);
		modelMap.addAttribute("currentNumber", 99);
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("baiduUrl", "http://www.baidu.com");
		modelMap.addAttribute("shopLogo",
				"http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
		modelMap.addAttribute("city", cities.get(0));
		modelMap.addAttribute("country", country);
		modelMap.addAttribute("updateCityUri", "/city/edit");
		modelMap.addAttribute("cities", cities);
		modelMap.addAttribute("template", "test/index");
		return "index";
	}

}
