package com.rosenzest.server.base.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fronttang
 * @date 2021/08/03
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

	@Pointcut("execution(public * com.rosenzest..controller..*.*(..))") // 切入点描述 这个是controller包的切入点
	public void controllerLog() {
	}

	/**
	 * 接口请求参数记录日志
	 */
	@Before("controllerLog()")
	public void doBefore(JoinPoint joinPoint) {

		IRequestContext current = RequestContextHolder.getCurrent();

		log.info("请求方法:{}", joinPoint.toLongString());
		log.info("请求参数:{}", JSON.toJSONString(current.getParam()));
	}

	/**
	 * 接口返回结果记录日志
	 */
	@AfterReturning(pointcut = "controllerLog()", returning = "result")
	public void doAfterReturning(JoinPoint joinPoint, Object result) {
		// log.info("返回数据:{}", JSON.toJSONString(result));
	}

	@Around("controllerLog()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			throw e;
		}
	}
}
