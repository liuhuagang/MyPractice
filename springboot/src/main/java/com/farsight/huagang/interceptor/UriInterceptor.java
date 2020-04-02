package com.farsight.huagang.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UriInterceptor implements HandlerInterceptor {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UriInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.debug("Pre interceptor.");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//如果是重定向到控制器 不需要再添加其余参数
		if (modelAndView == null || modelAndView.getViewName().startsWith("redirect:")) {
			return;
		}
		
		LOGGER.debug("Post interceptor.");
		String uri = request.getServletPath();
		if (StringUtils.isNotBlank(uri)) {
			if (uri.startsWith("/")) {
				uri = uri.substring(1);
			}
		}
		String template = (String) modelAndView.getModelMap().get("template");
		if (StringUtils.isBlank(template)) {
			modelAndView.getModelMap().addAttribute("template", uri.toLowerCase());
		}
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LOGGER.debug("After interceptor.");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
