/**
 * 
 */
package com.farsight.huagang.moduls.test.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.farsight.huagang.moduls.test.entity.Country;

/**
 * @author  liu
 *	@date  2020年4月1日
 *	@Description   通过xml实现mybaits框架
 *	@version  
 */
@Repository
@Mapper
public interface CountryDaoByXml {
	Country getCountryById(int countryId);
}
