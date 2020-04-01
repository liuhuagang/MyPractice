/**
 * 
 */
package com.farsight.huagang.moduls.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.farsight.huagang.moduls.test.entity.City;
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
	 * 占位符预编译
	 * ${countryId} ---- select * from m_country where country_id = 3  
	 * 字符串的拼接 多用于排序或则限制查询等
	 * 
	 * 
	 * 	@Results ---- 封装结果集，对于联表查询的字段，可调用已有的方法getCitiesByCountryId
	 * column ---- 对应 select 查询后的某个字段名，作为映射实体bean属性 或者 作为调用方法的参数
	 * property ---- 对应 实体 bean 属性
	 * 1、country_id封装了两次，分别对应countryId和cities，而cities属性通过getCitiesByCountryId方法来实现，
	 * country_id作为参数
	 * 2、结果集共享，设置id属性，调用是使用@ResultMap(value="countryResult")
	 */

	/**
	 * 
	 * @Description  一对多的注解查询  调用了另一个查询城市的方法,然后把每一个城市封装进去
	 * @param countryId
	 * @return
	 */
	@Select("select * from m_country where country_id = #{countryId}")
	@Results(id = "countryResult", value = { 
			@Result(column = "country_id", property = "countryId"),
			@Result(column = "country_id", property = "cities", 
			javaType = List.class, 
			many = @Many(select = "com.farsight.huagang.moduls.test.dao.CountryDao."
					+ "getCitiesByCountryId")) })
	Country getCountryById(int countryId);

	@Select("select * from m_city where country_id = #{countryId}")
	List<City> getCitiesByCountryId(int countryId);
	
	
	/**
	 * 
	 * @Description  通过国家名称进行查询，同时封装结果集映射同上
	 * @param countryName
	 * @return
	 */
	@Select("select * from m_country where country_name = #{countryName}")
	@ResultMap(value="countryResult")
	Country getCountryByName(String countryName);
	
	/**
	 * 
	 * @Description  插入时候开启了主键ID自动读取返回的这个功能
	 * @param city
	 */
	@Insert("insert into m_city (city_name, local_city_name, country_id, date_created) "
			+ "values (#{cityName}, #{localCityName}, #{countryId}, #{dateCreated})")
	@Options(useGeneratedKeys=true, keyColumn="city_id", keyProperty="cityId")
	void insertCity(City city);
	
	/**
	 * 
	 * @Description  只更新了name
	 * @param city
	 * 注意多个参数时不为一个对象，可以采用@Param……"cityName) String cityName……
	 */
	@Update("update m_city set local_city_name = #{localCityName} where city_id = #{cityId}")
	void updateCity(City city);
	
	@Delete("delete from m_city where city_id=#{cityId}")
	void deleteCity(int cityId);
	
	
}
