package com.farsight.huagang.moduls.common.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farsight.huagang.moduls.common.vo.Result;
import com.farsight.huagang.moduls.common.vo.Result.ResultStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandleRresolve {

	@ExceptionHandler(value=AuthorizationException.class)
	@ResponseBody
	public Result handlerAuthorizationException(RedirectAttributes redirectAttributes) {
		Result result = new Result(ResultStatus.FAILED.status, "", "/403");
		return result;
	}
}
