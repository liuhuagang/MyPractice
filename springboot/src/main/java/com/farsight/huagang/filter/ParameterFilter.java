package com.farsight.huagang.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这是过滤器的类
 */
@WebFilter(filterName = "parameterFilter", urlPatterns = "/**")
public class ParameterFilter implements Filter {

	private final static Logger LOGGER = LoggerFactory.getLogger(ParameterFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.debug("Init parameter filter.");
		Filter.super.init(filterConfig);
	}

	@Override
	public void destroy() {
		LOGGER.debug("Dest parameter filter.");
		Filter.super.destroy();
	}

	//通过注解的方式获取时需要重写以下方法
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		//		Map<String, String[]> map = httpRequest.getParameterMap();
		//		map.put("testKey", new String[]{"*****"});

		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
			//通过request访问的时候获取屏蔽时需要重写以下方法
			@Override
			public String getParameter(String name) {
				String value = httpRequest.getParameter(name);
				if (value != null || value != "") {
					return value.replace("fuck", "***");
				}
				return super.getParameter(name);
			}

			//通过注解的方式获取时需要重写以下方法
			@Override
			public String[] getParameterValues(String name) {
				String[] values = httpRequest.getParameterValues(name);
				if (values != null && values.length > 0) {
					values[0] = values[0].replace("fuck", "***");
					return values;
				}
				return super.getParameterValues(name);
			}

		};

		chain.doFilter(wrapper, response);
	}

}
