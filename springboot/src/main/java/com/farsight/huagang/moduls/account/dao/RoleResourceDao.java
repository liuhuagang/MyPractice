/**
 * 
 */
package com.farsight.huagang.moduls.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author  liu
 *	@date  2020年4月9日
 *	@Description   
 *	@version  
 */
@Repository
@Mapper
public interface RoleResourceDao {

	/**
	 * @Description  
	 * @param resourceId
	 */
	@Delete("delete from m_role_resource where resource_id = #{resourceId}")
	void deleteRoleResourceByResourceId(int resourceId);

	/**
	 * @Description  
	 * @param resourceId
	 * @param roleId
	 */
	@Insert("insert m_role_resource(resource_id, role_id) value(#{resourceId}, #{roleId})")
	void insertRoleResource(@Param("resourceId")int resourceId, @Param("roleId")int roleId);

}
