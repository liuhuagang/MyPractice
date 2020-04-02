/**
 * 
 */
package com.farsight.huagang.moduls.test.service;

import java.util.List;

import com.farsight.huagang.moduls.test.entity.City;
import com.farsight.huagang.moduls.test.entity.Country;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年3月31日
 *	@Description   
 *	@version  
 */

public interface CountryService {

	Country getCountryById(int countryId);

	Country getCountryByIdByXML(int countryId);

	Country getCountryByName(String countryName);

	PageInfo<City> getCitiesByPage(int countryId, int currentPage, int pageSize);

	City insertCity(City city);

	City updateCity(City city);

	void deleteCity(int cityId);

	/**
	 * @Description  
	 * @param countryId
	 * @return
	 */
	List<City> getCitiesByCountryId(int countryId);
}
