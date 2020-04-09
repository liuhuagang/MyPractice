/**
 * 
 */
package com.farsight.huagang.moduls.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.User;

/**
 * @author  liu
 *	@date  2020年4月5日
 *	@Description   
 *	@version  
 */
@Repository
@Mapper
public interface AccountDao {

	/**
	 * @Description  
	 * @param userName
	 * @return
	 */

	@Select("select * from m_user where user_name = #{userName}")
	public User findUserByUserName(String userName);

	/**
	 * @Description  
	 * @param user
	 * @return
	 */
	@Insert("insert into m_user(user_name,password,create_date) " + "values (#{userName},#{password},#{createDate})")
	@Options(useGeneratedKeys = true, keyColumn = "user_id", keyProperty = "userId")
	public int addUser(User user);

	/**
	 * @Description  
	 * @param userName
	 * @return
	 */
	@Select("SELECT r.role_id,role_name from m_user u,m_user_role ur,m_role r "
			+ "where u.user_id =ur.user_id and ur.role_id=r.role_id and u.user_name=#{userName}")
	public List<Role> selectRolesByUserName(String userName);

	/**
	 * @Description  
	 * @param roleId
	 * @return
	 */
	@Select("select r.resource_id ,r.resource_name,r.resource_uri,r.permission from m_role_resource rr,m_resource r "
			+ "where rr.resource_id=r.resource_id and rr.role_id=#{roleId} ")
	public List<Resource> selecResourceByRoleId(int roleId);

	/**
	 * @Description  
	 * @return
	 */
	@Select("SELECT * from m_user ")
	@Results(id = "userResult", value = { 
			@Result(column = "user_id", property = "userId"),
			@Result(column = "user_id", property = "roles", 
			javaType = List.class, many = @Many(select = "com.farsight.huagang.moduls.account.dao.AccountDao.selectRolesByUserId")) })
	public List<User> selectAllUsers();

	@Select("SELECT r.role_id,role_name from m_user u,m_user_role ur,m_role r "
			+ "where u.user_id =ur.user_id and ur.role_id=r.role_id and u.user_id=#{userId}")
	public List<Role> selectRolesByUserId(int userId);

	/**
	 * @Description  
	 * @return
	 */
	@Select("SELECT * from m_role ")
	public List<Role> selectAllRoles();

	/**
	 * @Description  
	 * @return
	 */
	@Select("SELECT * from m_resource ")
	public List<Resource> selectAllResources();

}
