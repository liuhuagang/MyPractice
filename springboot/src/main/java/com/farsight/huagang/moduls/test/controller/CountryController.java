/**
 * 
 */
package com.farsight.huagang.moduls.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farsight.huagang.moduls.test.entity.City;
import com.farsight.huagang.moduls.test.entity.Country;
import com.farsight.huagang.moduls.test.service.CountryService;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年3月31日
 *	@Description   
 *	@version  
 */
@RestController
public class CountryController {

	@Autowired
	private CountryService countryService;

	/**
	 * 127.0.0.1:8085/country/522
	 */
	@RequestMapping("/country/{countryId}")
	public Country getCountryById(@PathVariable int countryId) {
		return countryService.getCountryById(countryId);
	}
	
	/**
	 * 127.0.0.1:8085/countryXML/522
	 */
	@RequestMapping("/countryXML/{countryId}")
	public Country getCountryByIdByxml(@PathVariable int countryId) {
		return countryService.getCountryById(countryId);
	}
	
	/**
	 * 127.0.0.1:8085/country?countryName=China
	 */
	@RequestMapping("/country")
	public Country getCountryByName(@RequestParam String countryName) {
		return countryService.getCountryByName(countryName);
	}
	
	/**
	 * 127.0.0.1:8085/cities?countryId=522&currentPage=1&pageSize=10
	 */
	@RequestMapping("/cities")
	PageInfo<City> getCitiesByPage(@RequestParam int countryId, 
			@RequestParam int currentPage, @RequestParam int pageSize) {
		return countryService.getCitiesByPage(countryId, currentPage, pageSize);
	}
	
	/**
	 * 插入多用post请求 @RequestBody application/json
	 * 127.0.0.1:8085/city
	 * {"cityName":"testName1","countryId":"522","dateCreated":"2019-08-27 14:38:39","localCityName":"bbb"}
	 */
//	@RequestMapping(value = "/city",method = RequestMethod.POST,consumes = "application/json")
	@PostMapping(value="/city", consumes="application/json")
	public City insertCity(@RequestBody City city) {
		countryService.insertCity(city);
		return city;
	}
	
	/**
	 * 更新多用put请求
	 * 127.0.0.1:8085/city
	 * form put请求，表单请求 @ModelAttribute application/x-www-form-urlencoded
	 * 注意不同请求可以对一个地址进行重名应用，但相同请求不可以
	 * id可以放到地址栏里面，
	 */
	@PutMapping(value="/city", consumes="application/x-www-form-urlencoded")
	public City updateCity(@ModelAttribute City city) {
		countryService.updateCity(city);
		return city;
	}
	/**
	 * 删除用的get请求。注意postman 写在了地址栏上即可
	 * @Description  
	 * @param cityId
	 */
	@DeleteMapping("/city/{cityId}")
	public void deleteCity(@PathVariable int cityId) {
		countryService.deleteCity(cityId);
	}
	
	
	
	
	
}
