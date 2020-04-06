/**
 * 
 */
package com.farsight.huagang.moduls.account.service.impl;

import org.springframework.stereotype.Service;

import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.service.RoleService;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */
@Service
public class RoleServiceImpl implements RoleService{

	@Override
	public Role selectRoleById(int roleId) {
		Role role = new Role();
		return role;
	}

}
