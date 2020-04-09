/**
 * 
 */
package com.farsight.huagang.moduls.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.farsight.huagang.moduls.account.entity.Role;

/**
 * @author  liu
 *	@date  2020年4月9日
 *	@Description   
 *	@version  
 */
@Repository
@Mapper
public interface UserRoleDao {

	/**
	 * @Description  
	 * @param userId
	 */
	@Delete("delete from m_user_role where user_id = #{userId}")
	void deleteUserToRole(int userId);

	/**
	 * @Description  
	 * @param userId
	 * @param roleId
	 */
	@Insert("insert m_user_role(role_id, user_id) value(#{roleId}, #{userId})")
	void insertUserRole(@Param("userId")int userId, @Param("roleId")int roleId);

	@Select("SELECT r.role_id ,r.role_name from m_role r,m_role_resource rs "
			+ "where r.role_id=rs.role_id and rs.resource_id=#{resourceId} ")
	List<Role> selectRolesByResourceId(int resourceId);
}
