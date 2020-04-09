package com.farsight.huagang.moduls.account.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farsight.huagang.moduls.account.dao.UserDao;
import com.farsight.huagang.moduls.account.dao.UserRoleDao;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.UserService;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.Result.ResultStatus;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao uRD;

	@Override
	public Result insertUser(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");

		User userTemp = userDao.getUserByUserName(user.getUserName());
		if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("User name is repeat.");
			return result;
		}

		user.setCreateDate(new Date());
		userDao.insertUser(user);
		//2.插入新的角色信息
		List<Role> roles = user.getRoles();
		if (!roles.isEmpty()) {
			for (Role role : roles) {
				uRD.insertUserRole(user.getUserId(), role.getRoleId());
			}
		}
		return result;
	}

	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public Result getUser(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");

		User userTemp = userDao.getUser(user);
		if (userTemp == null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("User name or password error.");
			return result;
		} else {
			result.setObject(userTemp);
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	@Override
	public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
		searchVo.initSearchVo(searchVo);
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		List<User> users = Optional.ofNullable(userDao.getUsersBySearchVo(searchVo)).orElse(Collections.emptyList());
		return new PageInfo(users);
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public Result updateUser(User user) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		User userTemp = userDao.getUserByUserName(user.getUserName());
		if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("User name is repeat.");
			return result;
		}
		//更新user 数据	
		userDao.updateUser(user);
		//更改角色信息
		//1.删除原先角色信息
		uRD.deleteUserToRole(user.getUserId());
		//2.插入新的角色信息
		List<Role> roles = user.getRoles();
		if (!roles.isEmpty()) {
			for (Role role : roles) {
				uRD.insertUserRole(user.getUserId(), role.getRoleId());
			}
		}

		return result;
	}

	@Override
	public Result deleteUser(int userId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "");
		try {
			userDao.deleteUser(userId);
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}
}
