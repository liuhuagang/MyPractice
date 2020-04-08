/**
 * 
 */
package com.farsight.huagang.moduls.account.service;

import com.farsight.huagang.moduls.account.entity.Role;
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
	public PageInfo<Role> getUsersBySearchVo(SearchVo searchVo);

}
