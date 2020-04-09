/**
 * 
 */
package com.farsight.huagang.moduls.account.service;

import java.util.List;

import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */

public interface RoleService {

	/**
	 * @Description  
	 * @param roleId
	 * @return
	 */
	public Role selectRoleById(int roleId);

	/**
	 * @Description  
	 * @param searchVo
	 * @return
	 */
	public PageInfo<Role> getRolesBySearchVo(SearchVo searchVo);

		
	Role getRoleById(int roleId);
	
	Result updateRole(Role role);
	
	Result deleteRole(int roleId);

	/**
	 * @Description  
	 * @param role
	 * @return
	 */
	public Result addRole(Role role);

	/**
	 * @Description  
	 * @return
	 */
	public List<Role> getRolesAll();
}
