/**
 * 
 */
package com.farsight.huagang.moduls.account.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farsight.huagang.moduls.account.dao.RoleDao;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.service.RoleService;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.SearchVo;
import com.farsight.huagang.moduls.common.vo.Result.ResultStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;

	@Override
	public Role selectRoleById(int roleId) {
		Role role = new Role();
		return role;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageInfo<Role> getRolesBySearchVo(SearchVo searchVo) {

		searchVo.initSearchVo(searchVo);
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		List<Role> roles = Optional.ofNullable(roleDao.getRolesBySearchVo(searchVo)).orElse(Collections.emptyList());
		return new PageInfo(roles);

	}

	@Override
	public Role getRoleById(int roleId) {

		return roleDao.getRoleById(roleId);
	}

	@Override
	public Result updateRole(Role role) {

		Result result = new Result(ResultStatus.SUCCESS.status, "更新成功");

		Role roleTemp = roleDao.getRoleByRoleName(role.getRoleName());
		if (roleTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("Role name is repeat.");
			return result;
		}

		roleDao.updateRole(role);

		return result;
	}

	@Override
	public Result deleteRole(int roleId) {
		Result result = new Result(ResultStatus.SUCCESS.status, "删除成功");
		try {
			roleDao.deleteRole(roleId);
		} catch (Exception e) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage(e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Result addRole(Role role) {
		Result result = new Result(ResultStatus.SUCCESS.status, "添加成功");

		Role roleTemp = roleDao.getRoleByRoleName(role.getRoleName());
		if (roleTemp != null) {
			result.setStatus(ResultStatus.FAILED.status);
			result.setMessage("Role name is repeat.");

		}
		roleDao.insertRole(role);
		return result;
	}

	@Override
	public List<Role> getRolesAll() {
		
		return roleDao.getRolesAll();
	}
}
