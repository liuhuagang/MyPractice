/**
 * 
 */
package com.farsight.huagang.moduls.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farsight.huagang.moduls.test.dao.CountryDao;
import com.farsight.huagang.moduls.test.entity.Country;
import com.farsight.huagang.moduls.test.service.CountryService;

/**
 * @author  liu
 *	@date  2020年3月31日
 *	@Description   
 *	@version  
 */
@Service
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryDao cD;

	@Override
	public Country getCountryById(int countryId) {
		return cD.getCountryById(countryId);
	}

}
