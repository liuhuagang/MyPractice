/**
 * 
 */
package com.farsight.huagang.moduls.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farsight.huagang.moduls.test.entity.Country;
import com.farsight.huagang.moduls.test.service.CountryService;

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
}
