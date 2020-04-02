/**
 * 
 */
package com.farsight.huagang.moduls.test.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@RequestMapping("/test/demoInfo")
	@ResponseBody
	public String demoDesc(HttpServletRequest request, @RequestParam String key) {
		//	String parameter2 = request.getParameter(null);
		String parameter = "";
		if (key != null) {
			parameter = request.getParameter("key");
		}

		return "This is a Spring Boot demo." + parameter + "--" + key;
	}

	/**
	 * 这里使用了重定向，存在request里面的数据会被刷新掉，所以没有使用Modelmap了而是使用了RedirectAttributes
	 * 在文件上传的时候，关于参数指定最好使用@RequestParam而不是@ModelAttribute 
	 * 数据格式需要指定，如果目标目标文件夹不存在会报错
	 * "java.io.IOException: java.io.FileNotFoundException": ../ava编程规范.pdf (系统找不到指定的路径。)
	 * org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException: 
	 * the request was rejected because its size (419830592) exceeds the configured maximum (10485760)
	 */

	/**
	 * 方法一
	 * @Bean
	public MultipartConfigElement multipartConfigElement() {
	    MultipartConfigFactory factory = new MultipartConfigFactory();
	    //单个文件最大
	    factory.setMaxFileSize("10240KB"); //KB,MB
	    /// 设置总上传数据总大小
	    factory.setMaxRequestSize("102400KB");
	    return factory.createMultipartConfig();
	}
	方法二
	#设置上传文件的大小
	servlet:
	multipart:
	max-file-size: 10MB
	max-request-size: 50MB
	
	 * 
	 */
	@PostMapping(value = "/test/upload", consumes = "multipart/form-data")
	public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select file.");
			return "redirect:/test/index";
		}

		String fileName = file.getOriginalFilename();

		File destFile = new File(String.format("D:\\upload\\%s", fileName));
		try {
			file.transferTo(destFile);
			redirectAttributes.addFlashAttribute("message", "upload success.");
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
			redirectAttributes.addFlashAttribute("message", "upload failed.");
		}

		return "redirect:/thy/test";
	}

}
