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

import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.common.vo.SearchVo;

/**
 * @author  liu
 *	@date  2020年4月8日
 *	@Description   
 *	@version  
 */
@Repository
@Mapper
public interface RoleDao {
	@Select("<script>" + "select * from m_role " + "<where> " + "<if test='keyWord != \"\" and keyWord != null'>"
			+ "and role_name like '%${keyWord}%' " + "</if>" + "</where>" + "<choose>"
			+ "<when test='orderBy != \"\" and orderBy != null'>" + "order by ${orderBy} ${sort}" + "</when>"
			+ "<otherwise>" + "order by create_date desc" + "</otherwise>" + "</choose>" + "</script>")
	List<Role> getRolesBySearchVo(SearchVo searchVo);

	@Insert("insert into m_role (role_name) " + "values (#{roleName})")
	@Options(useGeneratedKeys = true, keyColumn = "role_id", keyProperty = "roleId")
	void insertRole(Role role);

	@Select("select * from m_role where role_id=#{roleId}")
	Role getRoleById(int roleId);

	@Select("select * from m_role where role_name = #{roleName}")
	Role getRoleByRoleName(String roleName);

	@Update("update m_role set role_name=#{roleName} where role_id=#{roleId}")
	void updateRole(Role role);

	@Select("delete from m_role where role_id=#{roleId}")
	void deleteRole(int roleId);

	/**
	 * @Description  
	 * @return
	 */
	@Select("select * from m_role")
	List<Role> getRolesAll();

}
