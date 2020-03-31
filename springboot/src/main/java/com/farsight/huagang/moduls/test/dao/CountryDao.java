/**
 * 
 */
package com.farsight.huagang.moduls.test.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.farsight.huagang.moduls.test.entity.Country;


/**
 * @author  liu
 *	@date  2020年3月31日
 *	@Description   
 *	@version  
 */
@Repository
@Mapper
public interface CountryDao {
	/**
	 * #{countryId} ---- select * from m_country where country_id = ?
	 * ${countryId} ---- select * from m_country where country_id = 3
	 */
	@Select("select * from m_country where country_id = #{countryId}")
	Country getCountryById(int countryId);
}
