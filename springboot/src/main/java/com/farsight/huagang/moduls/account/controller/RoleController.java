/**
 * 
 */
package com.farsight.huagang.moduls.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.service.RoleService;
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
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping(value = "/roles", consumes = "application/json")
	public PageInfo<Role> getUsersBySearchVo(@RequestBody SearchVo searchVo) {
		return roleService.getRolesBySearchVo(searchVo);
	}
	
	@GetMapping(value="/roles",consumes = "application/json")
	public List<Role> getRolesAll(){
		return roleService.getRolesAll();
	}

	@RequestMapping("/role/{roleId}")
	public Role getUserById(@PathVariable int roleId) {
		return roleService.getRoleById(roleId);
	}

	@PostMapping(value = "/role", consumes = "application/json")
	public Result addRole(@RequestBody Role role) {
		return roleService.addRole(role);
	}

	@PutMapping(value = "/role", consumes = "application/json")
	public Result updateRole(@RequestBody Role role) {
		return roleService.updateRole(role);
	}

	@DeleteMapping("/role/{roleId}")
	public Result deleteUser(@PathVariable int roleId) {
		return roleService.deleteRole(roleId);
	}
}
