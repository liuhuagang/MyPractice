/**
 * 
 */
package com.farsight.huagang.moduls.test.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.farsight.huagang.moduls.test.dao.CountryDao;
import com.farsight.huagang.moduls.test.dao.CountryDaoByXml;
import com.farsight.huagang.moduls.test.entity.City;
import com.farsight.huagang.moduls.test.entity.Country;
import com.farsight.huagang.moduls.test.service.CountryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	@Autowired
	private CountryDaoByXml cDBX;

	@Override
	public Country getCountryById(int countryId) {
		return cD.getCountryById(countryId);
	}

	@Override
	public Country getCountryByIdByXML(int countryId) {

		return cDBX.getCountryById(countryId);
	}

	@Override
	public Country getCountryByName(String countryName) {

		return cD.getCountryByName(countryName);
	}

	@Override
	public PageInfo<City> getCitiesByPage(int countryId, int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<City> cities = cD.getCitiesByCountryId(countryId);
		//不用采用以下写法，myBaits已经封装了集合为空的时候 创建一个长度为0的集合，但如果某个对象则为null；
		//	List<City> cities = Optional.ofNullable(cD.getCitiesByCountryId(countryId)).orElse(Collections.emptyList());
		return new PageInfo<City>(cities);
	}

	@Override
	public City insertCity(City city) {
		cD.insertCity(city);
		return city;
	}

	/**
	 * 开启了事务支持，同时开启了不回滚除数为0的异常
	 */
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor = ArithmeticException.class)
	@Override
	public City updateCity(City city) {
		cD.updateCity(city);
		int i = 1 / 0;
		return city;
	}

	@Override
	public void deleteCity(int cityId) {

		cD.deleteCity(cityId);
	}

}
