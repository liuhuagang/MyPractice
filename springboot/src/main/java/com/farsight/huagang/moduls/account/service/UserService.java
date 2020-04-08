package com.farsight.huagang.moduls.account.service;

import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.github.pagehelper.PageInfo;

public interface UserService {
	
	Result insertUser(User user);
	
	User getUserByUserName(String userName);
	
	Result getUser(User user);
	
	PageInfo<User> getUsersBySearchVo(SearchVo searchVo);
	
	
	User getUserById(int userId);
	
	Result updateUser(User user);
	
	Result deleteUser(int userId);
}
