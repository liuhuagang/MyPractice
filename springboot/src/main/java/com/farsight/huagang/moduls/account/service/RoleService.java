/**
 * 
 */
package com.farsight.huagang.moduls.account.service;

import com.farsight.huagang.moduls.account.entity.Role;

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

}
