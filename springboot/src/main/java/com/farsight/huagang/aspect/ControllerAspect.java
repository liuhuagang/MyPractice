package com.farsight.huagang.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ControllerAspect {

	private final static Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

	/**
	 * 关联在方法上的切点
	 * 第一个*代表返回类型不限
	 * 第二个*代表module下所有子包
	 * 第三个*代表所有类
	 * 第四个*代表所有方法
	 * (..) 代表参数不限
	 * Order 代表优先级，数字越小优先级越高
	 */
	@Pointcut("execution(public * com.farsight.huagang.moduls.*.controller.*.*(..))")
	@Order(1)
	public void controllerPointCut() {
	}

	@Before(value = "com.farsight.huagang.aspect.ControllerAspect.controllerPointCut()")
	public void beforeController(JoinPoint joinPoint) {
		LOGGER.debug("=======================");
		LOGGER.debug("Before controller");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		LOGGER.debug("请求来源：" + request.getRemoteAddr());
		LOGGER.debug("请求URL：" + request.getRequestURL().toString());
		LOGGER.debug("请求方式：" + request.getMethod());
		LOGGER.debug(
				"响应方法：" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		LOGGER.debug("请求参数：" + Arrays.toString(joinPoint.getArgs()));
	}

	/**
	 * -环绕通知 = 前置 + 目标方法执行 + 后置通知
	 * ProceedingJoinPoint 继承了 JoinPoint，是在 JoinPoint 的基础上暴露出  proceed 这个方法，
	 * -这个是 aop 代理链执行的方法， 执行proceed方法的作用是让目标方法执行，这也是环绕通知和前置、后置通知方法的一个最大区别;
	 */
	/**
	 *      spring 的环绕通知和前置通知，后置通知有着很大的区别，主要有两个重要的区别：
	
	1） 目标方法的调用由环绕通知决定，即你可以决定是否调用目标方法，而前置和后置通知   是不能决定的，他们只是在方法的调用前后执行通知而已，即目标方法肯定是要执行的。
	
	2）  环绕通知可以控制返回对象，即你可以返回一个与目标对象完全不同的返回值，虽然这很危险，但是你却可以办到。而后置方法是无法办到的，因为他是在目标方法返回值后调用
	 * @Description  
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "com.farsight.huagang.aspect.ControllerAspect.controllerPointCut()")
	public Object aroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		LOGGER.debug("Around controller");
		return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
	}

	/**
	 * -后置通知
	 */
	@After(value = "com.farsight.huagang.aspect.ControllerAspect.controllerPointCut()")
	public void afterController() {
		LOGGER.debug("After Controller.");
		LOGGER.debug("=======================");
	}
}
