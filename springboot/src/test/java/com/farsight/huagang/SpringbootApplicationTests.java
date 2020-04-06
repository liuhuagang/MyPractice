package com.farsight.huagang;

import java.util.List;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.farsight.huagang.moduls.account.dao.AccountDao;
import com.farsight.huagang.moduls.account.entity.Resource;
import com.farsight.huagang.moduls.account.entity.Role;
import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.AccountService;
import com.farsight.huagang.moduls.account.util.MD5Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {
	@Autowired
	private AccountService uS;

	@Autowired
	private AccountDao aD;

	@Autowired
	private ServletContext servletContext;

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootApplicationTests.class);

	@Test
	public void contextLoads() {
	}

	@Test
	public void contextTest() {
		LOGGER.warn("信息测试");
		LOGGER.warn(servletContext.getContextPath());
	}

	@Test
	public void MD5() {

		String passwordByMD5 = MD5Util.getPasswordByMD5("123", "root");
		System.out.println("MD5");
		System.out.println(passwordByMD5);
	}

	@Test
	public void rolesTest() {
		List<Role> roles = uS.selectRolesByUserName("test01");

		System.out.println(roles);
	}

	@Test
	public void resourceTest() {
		List<Resource> resources = uS.selecResourceByRoleId(1);
		System.out.println(resources);
	}

	@Test
	public void usersTest() {
		List<User> users = aD.selectAllUsers();
		System.out.println(users.get(0));
		System.out.println(users.get(1));
	}

}
