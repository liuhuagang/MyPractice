/**
 * 
 */
package com.farsight.huagang.moduls.account.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.UserService;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年4月8日
 *	@Description   
 *	@version  
 */
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/users", consumes = "application/json")
	public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo) {
		return userService.getUsersBySearchVo(searchVo);
	}


	@RequestMapping("/user/{userId}")
	public User getUserById(@PathVariable int userId) {
		return userService.getUserById(userId);
	}

	@PutMapping(value = "/user", consumes = "application/json")
	public Result updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@RequiresRoles(value = {"admin"})
	@DeleteMapping("/user/{userId}")
		public Result deleteUser(@PathVariable int userId) {
		return userService.deleteUser(userId);
	}
}
