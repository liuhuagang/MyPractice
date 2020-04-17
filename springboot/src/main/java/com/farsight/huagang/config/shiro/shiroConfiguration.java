/**
 * 
 */
package com.farsight.huagang.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */
@Configuration
public class shiroConfiguration {

	@Bean
	public MyRealm myRealm() {
		MyRealm myRealm = new MyRealm();
		myRealm.setCredentialsMatcher(credentialsMatcher());
		return myRealm;

	}

	//	<!-- 凭证匹配器 -->
	@Bean
	public HashedCredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(1024);
		return hashedCredentialsMatcher;

	}

	@Bean
	public CookieRememberMeManager rememberMeManager() {

		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		return cookieRememberMeManager;
	}

	//记住我的cookie
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}

	//权限管理，配置Reaml的管理认证
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm());
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	//Filter工厂，设置对应的过滤条件和跳转条件

	/**
	 * --配置shiro过滤器工厂
	 * -----------------
	 * --拦截权限
	 * anon：匿名访问，无需登录
	 * authc：登录后才能访问
	 * user：登录过能访问
	 * logout：登出 可以不写controller 只要配置了路径
	 * roles：角色过滤器
	 * ------------------
	 * URL匹配风格
	 * ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/
	 * *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1
	 * **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
	 * -----------------------
	 * --方法名不能乱写，如果我们定义为别的名称，又没有添加注册过滤器的配置，
	 * --那么shiro会加载ShiroWebFilterConfiguration过滤器，
	 * --该过滤器会寻找shiroFilterFactoryBean，找不到会抛出异常
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//	Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();//获取filters
		//	filters.put("authc", authc());//将自定义 的FormAuthenticationFilter注入shiroFilter中  

		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//注意map集合必须有序，不然会导致错误拦截
		Map<String, String> map = new LinkedHashMap<String, String>();
		//对于静态资源进行放开
		//登出
		//	map.put("/account/logout", "logout");
		//登录页面
		map.put("/account/gologin", "anon");
		map.put("/account/register", "anon");
		map.put("/account/doRegister", "anon");
		map.put("/account/login", "anon");
		//对于静态资源进行放开
		map.put("/static/**", "anon");
		map.put("/account/images/**", "anon");
		map.put("/js/**", "anon");
		map.put("/css/**", "anon");
		map.put("/font-awesome/**", "anon");
		map.put("/fonts/**", "anon");
		map.put("/images/**", "anon");
		map.put("/js/**", "anon");
		map.put("/swf/**", "anon");
		// 如果使用“记住我功能”，则采用user规则，如果必须要用户登录，则采用authc规则
		map.put("/**", "user");
		//	map.put("/**", "authc");
		//		map.put("/pay/**", "authc");
		//登录
		shiroFilterFactoryBean.setLoginUrl("/account/gologin");
		//首页
		shiroFilterFactoryBean.setSuccessUrl("/account/index");

		//错误页面，认证不通过跳转。这个地方跟过滤器有关
	shiroFilterFactoryBean.setUnauthorizedUrl("/error");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	//加入注解的使用，不加入这个注解不生效，原本是在spring mvc中开启的
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * --注册shiro方言，让thymeleaf支持shiro标签
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	/**
	 * --自动代理类，支持Shiro的注解
	 */
	@Bean
	@DependsOn({ "lifecycleBeanPostProcessor" })
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

}
