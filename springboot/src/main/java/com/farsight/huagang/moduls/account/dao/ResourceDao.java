/**
 * 
 */
package com.farsight.huagang.moduls.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.common.vo.SearchVo;

/**
 * @author  liu
 *	@date  2020年4月8日
 *	@Description   
 *	@version  
 */
@Repository
@Mapper
public interface ResourceDao {

	@Select("select * from m_resource where resource_name = #{resourceName}")
	Resource getResourceByResourceName(String resourceName);

	@Select("<script>" + "select * from m_resource " + "<where> " + "<if test='keyWord != \"\" and keyWord != null'>"
			+ "and resource_name like '%${keyWord}%' " + "</if>" + "</where>" + "<choose>"
			+ "<when test='orderBy != \"\" and orderBy != null'>" + "order by ${orderBy} ${sort}" + "</when>"
			+ "<otherwise>" + "order by create_date desc" + "</otherwise>" + "</choose>" + "</script>")
	List<Resource> getResourcesBySearchVo(SearchVo searchVo);

	@Select("select * from m_resource where resource_id=#{resourceId}")
	Resource getResourceById(int resourceId);

	@Update("update m_resource set resource_uri=#{resourceUri},resource_name=#{resourceName},permission=#{permission} where resource_id=#{resourceId}")
	void updateResource(Resource resource);

	@Select("delete from m_resource where resource_id=#{resourceId}")
	void deleteResource(int resourceId);

	/**
	 * @Description  
	 * @param resource
	 */
	@Insert("insert into m_resource (resource_uri, resource_name, permission) "
			+ "values (#{resourceUri}, #{resourceName}, #{permission})")
	@Options(useGeneratedKeys=true, keyColumn="resource_id", keyProperty="resourceId")
	void insertResource(Resource resource);
	
}
