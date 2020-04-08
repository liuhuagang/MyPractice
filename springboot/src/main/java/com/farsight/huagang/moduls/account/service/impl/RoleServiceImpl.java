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
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.RoleService;
import com.farsight.huagang.moduls.common.vo.SearchVo;
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
	public PageInfo<Role> getUsersBySearchVo(SearchVo searchVo) {

		searchVo.initSearchVo(searchVo);
		PageHelper.startPage(searchVo.getCurrentPage(), searchVo.getPageSize());
		List<Role> roles = Optional.ofNullable(roleDao.getRolesBySearchVo(searchVo)).orElse(Collections.emptyList());
		return new PageInfo(roles);

	}

}
