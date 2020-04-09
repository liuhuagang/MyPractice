/**
 * 
 */
package com.farsight.huagang.moduls.account.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farsight.huagang.moduls.account.dao.AccountDao;
import com.farsight.huagang.moduls.account.dao.UserRoleDao;
import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.AccountService;
import com.farsight.huagang.moduls.account.util.MD5Util;
import com.farsight.huagang.moduls.common.vo.Result;
import com.github.pagehelper.util.StringUtil;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao uD;
	@Autowired
	private UserRoleDao uRD;
	@Override
	public User selectUserByName(String userName) {

		return uD.findUserByUserName(userName);
	}

	@Override
	public boolean login(User user, String rememberMe) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
		if (rememberMe != null && "remember".equals(rememberMe)) {
			token.setRememberMe(true);
		}
		try {
			subject.login(token);
			subject.checkRoles();
			Session session = subject.getSession();
			session.setAttribute("loginUser", user);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	@Override
	public Result addUser(User user) {
		//非空判断
		if (user == null || StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())) {
			return new Result(500, "参数不可为空");
		}
		//用户名重复判断
		User userRepition = uD.findUserByUserName(user.getUserName());
		if (userRepition != null) {
			return new Result(500, "用户名重复");
		}
		//密码加密
		user.setPassword(MD5Util.getPasswordByMD5(user.getPassword(), user.getUserName()));
		//设置创建时间
		user.setCreateDate(new Date());
		//添加用户
		int num = uD.addUser(user);
		

		List<Role> roles = user.getRoles();
		if (!roles.isEmpty()) {
			for (Role role : roles) {
				uRD.insertUserRole(user.getUserId(), role.getRoleId());
			}
		}
		
		
		return num == 1 ? new Result(200, "注册成功") : new Result(500, "未知错误");
	}

	@Override
	public List<Role> selectRolesByUserName(String userName) {

		return uD.selectRolesByUserName(userName);
	}

	@Override
	public List<Resource> selecResourceByRoleId(int roleId) {

		return uD.selecResourceByRoleId(roleId);
	}

	@Override
	public Result selectAllUsers() {
		List<User> users = uD.selectAllUsers();

		return new Result(200, "成功载入", users);
	}

	@Override
	public Result selectAllRoles() {
		List<Role> users = uD.selectAllRoles();

		return new Result(200, "成功载入", users);
	}

	@Override
	public Result selectAllResources() {
		List<Resource> users = uD.selectAllResources();

		return new Result(200, "成功载入", users);
	}

}
