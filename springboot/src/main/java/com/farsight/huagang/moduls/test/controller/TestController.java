/**
 * 
 */
package com.farsight.huagang.moduls.test.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
	/**
	 * 
	 * @Description  上传多个文件
	 * @param files
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping(value="/test/uploadBatchFile", consumes="multipart/form-data")
	public String uploadBatchFile(@RequestParam MultipartFile[] files, 
			RedirectAttributes redirectAttributes) {
		
		boolean isEmpty = true;
		try {
			for (MultipartFile file : files) {
				if (file.isEmpty()) {
					continue;
				}
				
				String fileName = file.getOriginalFilename();
				File destFile = new File(String.format("D:\\upload\\%s", fileName));
				file.transferTo(destFile);
				
				isEmpty = false;
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "upload failed.");
			return "redirect:/thy/test";
		}
		
		if (isEmpty) {
			redirectAttributes.addFlashAttribute("message", "Please select file.");
		} else {
			redirectAttributes.addFlashAttribute("message", "upload success.");
		}
		
		
		return "redirect:/thy/test";
	}
	

	/*
	 * 三种下载方式 
	 * 文件名中文乱码都已经处理
	 */
	@RequestMapping("/test/download")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(HttpServletRequest request,@RequestParam String fileName) {
		try {
		
			Resource resource = new UrlResource(
					Paths.get(String.format("D:\\upload\\%s", fileName)).toUri());
		
			fileName=this.getFilename(request, fileName);
			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
						.header(HttpHeaders.CONTENT_DISPOSITION, 
								String.format("attachment; filename=\"%s\"", fileName))
						.body(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 将文件以BufferedInputStream的方式读取到byte[]里面，然后用OutputStream.write输出文件
	 * @throws Exception 
	 */
	@RequestMapping("/test/download1")
	public void downloadFile1(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam String fileName) throws Exception {
		String filePath = "D:/upload" + File.separator + fileName;
		File downloadFile = new File(filePath);
		
		if (downloadFile.exists()) {
			response.setContentType("application/octet-stream");
			response.setContentLength((int)downloadFile.length());
			//处理文件名中文乱码
			fileName=this.getFilename(request, fileName);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					String.format("attachment; filename=\"%s\"", fileName));
			
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(downloadFile);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (bis != null) {
						bis.close();
					}
				} catch (Exception e2) {
					LOGGER.debug(e2.getMessage());
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 以包装类 IOUtils 输出文件
	 */
	@RequestMapping("/test/download2")
	public void downloadFile2(HttpServletRequest request, 
			HttpServletResponse response, @RequestParam String fileName) {
		String filePath = "D:/upload" + File.separator + fileName;
		File downloadFile = new File(filePath);
		
		try {
			if (downloadFile.exists()) {
				response.setContentType("application/octet-stream");
				response.setContentLength((int)downloadFile.length());
				//处理文件名中文乱码
				fileName=this.getFilename(request, fileName);
				
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
						String.format("attachment; filename=\"%s\"", fileName));
				
				InputStream is = new FileInputStream(downloadFile);
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据浏览器的不同进行编码设置，返回编码后的文件名
	 */
	public String getFilename(HttpServletRequest request, String filename) throws Exception {
		// IE不同版本User-Agent中出现的关键词
		String[] IEBrowserKeyWords = { "MSIE", "Trident", "Edge" };
		// 获取请求头代理信息
		String userAgent = request.getHeader("User-Agent");
		for (String keyWord : IEBrowserKeyWords) {
			if (userAgent.contains(keyWord)) {
				//IE内核浏览器，统一为UTF-8编码显示
				return URLEncoder.encode(filename, "UTF-8");
			}
		}
		//火狐等其它浏览器统一为ISO-8859-1编码显示
		return new String(filename.getBytes("UTF-8"), "ISO-8859-1");
	}

}
