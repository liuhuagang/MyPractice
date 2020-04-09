/**
 * 
 */
package com.farsight.huagang.moduls.account.service;

import java.util.List;

import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.common.vo.Result;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */

public interface AccountService {

	/**
	 * @Description  
	 * @param userName
	 * @return
	 */
	public User selectUserByName(String userName);

	/**
	 * @Description  
	 * @param user
	 * @param rememberMe
	 * @return
	 */
	public Result login(User user);

	/**
	 * @Description  
	 * @param user
	 * @return
	 */
	public Result addUser(User user);

	/**
	 * @Description  
	 * @param userName
	 * @return
	 */
	public List<Role> selectRolesByUserName(String userName);

	/**
	 * @Description  
	 * @param roleId
	 * @return
	 */
	public List<Resource> selecResourceByRoleId(int roleId);

	/**
	 * @Description  
	 */
	public Result selectAllUsers();

	/**
	 * @Description  
	 * @return
	 */
	public Result selectAllRoles();

	/**
	 * @Description  
	 * @return
	 */
	public Result selectAllResources();

	/**
	 * @Description  
	 */
	public void logout();

}
