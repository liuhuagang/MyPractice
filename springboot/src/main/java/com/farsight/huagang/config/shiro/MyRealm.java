/**
 * 
 */
package com.farsight.huagang.config.shiro;

import java.awt.Menu;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.RoleService;
import com.farsight.huagang.moduls.account.service.AccountService;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */

public class MyRealm extends AuthorizingRealm {

	@Autowired
	private AccountService aS;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//读取用户名，页面输入
		String userName = (String) principals.getPrimaryPrincipal();

		/*	从数据库中读取数据*/
		//查出所有角色信息
		List<Role> roles = aS.selectRolesByUserName(userName);
		Set<String> roleNames = new HashSet<String>();
		//把角色名添加到Set
		for (Role role : roles) {
			roleNames.add(role.getRoleName());
			//遍历所有权限信息通过角色id
			List<Resource> resources = aS.selecResourceByRoleId(role.getRoleId());
			//将权限名称添加到授权认证中
			for (Resource resource : resources) {
				simpleAuthorizationInfo.addStringPermission(resource.getPermission());
			}
		}

		//将角色的名字写入到授权认证中
		simpleAuthorizationInfo.addRoles(roleNames);
		return simpleAuthorizationInfo;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//从token中，获取用户身份信息
		String username = (String) token.getPrincipal();

		//与数据数据进行匹配
		User user = aS.selectUserByName(username);
		if (user == null) {
			return null;
		}
		ByteSource salt = ByteSource.Util.bytes(username);
		//返回认证信息由父类AuthenticatingRealm进行认证
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(),
				salt, getName());
		return simpleAuthenticationInfo;
	}

	/*
	 * 	当用户权限修改后，用户再次登陆shiro会自动调用realm从数据库获取权限数据，如果在修改权限后想立即清除缓存则可以调用realm的clearCache方法清除缓存。
	 * 
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法。
	 */
	//清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}
