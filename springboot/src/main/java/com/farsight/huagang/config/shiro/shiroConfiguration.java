/**
 * 
 */
package com.farsight.huagang.config.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.servlet.Filter;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author  liu
 *	@date  2020年4月3日
 *	@Description   
 *	@version  
 */
@Configuration
public class shiroConfiguration {

	//将realm配置到容器中
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

	/*	public FormAuthenticationFilter authc() {
			FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
			formAuthenticationFilter.setName("authc");
			formAuthenticationFilter.setUsernameParam("userName");
			return formAuthenticationFilter;
		}
	*/
	//记住我的cookie
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	//权限管理，配置Reaml的管理认证
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm());
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	//Filter工厂，设置对应的过滤条件和跳转条件
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
		map.put("/account/logout", "logout");
		map.put("/account/gologin", "anon");
		map.put("/account/register", "anon");
		map.put("/account/doRegister", "anon");
		map.put("/account/login", "anon");
		//对于静态资源进行放开
		map.put("/static/**", "anon");
		map.put("/js/**", "anon");
		map.put("/css/**", "anon");
		//对所有用户认证
		map.put("/**", "user");
		//登录
		shiroFilterFactoryBean.setLoginUrl("/account/gologin");
		//首页
		shiroFilterFactoryBean.setSuccessUrl("/account/index");

		//错误页面，认证不通过跳转
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

}
