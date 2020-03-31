/**
 * 
 */
package com.farsight.huagang.moduls.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.farsight.huagang.moduls.test.vo.TestConfig;

/**
 * @author  liu
 *	@date  2020年3月30日
 *	@Description   
 *	@version  
 */
@Controller
public class TestController {
	@Value("${server.port}")
	private int port;
	@Autowired
	private TestConfig tCF;

	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@RequestMapping("/test/log")
	@ResponseBody
	public String logTest() {
		LOGGER.trace("This is trace log.");
		LOGGER.debug("This is debug log.");
		LOGGER.info("This is info log.1111111111111");
		LOGGER.warn("This is warn log.");
		LOGGER.error("This is error log.");
		return "This is log test.";
	}

	@RequestMapping("/test/demo")
	@ResponseBody
	public String test() {
		LOGGER.debug("测试成功");
		return "测试成功变更" + port + tCF.getName();
	}
}
