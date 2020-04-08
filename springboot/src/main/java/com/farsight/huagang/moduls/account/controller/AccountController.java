/**
 * 
 */
package com.farsight.huagang.moduls.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farsight.huagang.moduls.account.entity.User;
import com.farsight.huagang.moduls.account.service.AccountService;
import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.Result.ResultStatus;
import com.farsight.huagang.moduls.test.controller.TestController;

/**
 * @author  liu
 *	@date  2020年4月4日
 *	@Description   
 *	@version  
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	AccountService uS;

	@GetMapping("/index")
	public String goIndex() {
		return "index";
	}

	@GetMapping("/gologin")
	public String goLogin(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/login");
		return "indexSimple";
	}



	@PostMapping(value = "/doRegister", consumes = "application/json")
	@ResponseBody
	public Result register(@RequestBody User user) {

		return uS.addUser(user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(@RequestBody User user, String remember, ModelAndView modelAndView) {

		boolean flag = uS.login(user, remember);
		if (flag) {

			return new Result(ResultStatus.SUCCESS.status, "登录成功");
		} else {
			return new Result(ResultStatus.FAILED.status, "用户名或密码错误");
		}
	}

	@GetMapping("/logout")
	public void Logout(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/login");
		return;
	}

	@GetMapping("/dashboard")
	public String dashboard(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/dashboard");
		return "index";
	}

	@GetMapping("/users")
	public String users(ModelMap modelMap) {
		return "index";
	}

	@PostMapping("/users/findall")
	@ResponseBody
	public Result selectAllUsers() {
		Result result = uS.selectAllUsers();
		return result;
	}

	@GetMapping("/roles")
	public String roles() {

		return "index";
	}

	@PostMapping("/roles/findall")
	@ResponseBody
	public Result selectAllRoles() {
		Result result = uS.selectAllRoles();
		return result;
	}

	@GetMapping("/resources")
	public String resources() {

		return "index";
	}

	@PostMapping("/resources/findall")
	@ResponseBody
	public Result selectAllResources(ModelMap modelMap) {
		Result result = uS.selectAllResources();
		return result;
	}
}
