package com.rosenzest.api.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.rosenzest.api.util.ApiUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * API日志AOP拦截
 * 
 * @author fronttang
 * @date 2021/09/30
 */
@Component
@Aspect
@Slf4j
public class ApiLogAspect {

    /**
     * API接口切入点
     */
    @Pointcut("this(com.rosenzest.api.IApi)")
    public void apiPointCut() {}

    /**
     * 记录接口请求日志
     * 
     * @param joinPoint
     */
    @Before("apiPointCut()")
    public void doBefore(JoinPoint joinPoint) {

        // StringBuilder apiName = new StringBuilder(joinPoint.getTarget().getClass().getName());
        //
        // Api api = AnnotatedElementUtils.findMergedAnnotation(joinPoint.getTarget().getClass(), Api.class);
        // if (Objects.nonNull(api)) {
        // if (StrUtil.isNotBlank(api.name())) {
        // apiName.append(SymbolConstant.LEFT_ROUND_BRACKETS).append(api.name())
        // .append(SymbolConstant.RIGHT_ROUND_BRACKETS);
        // }
        // }

        log.info("调用接口:{}", ApiUtils.getApiName(joinPoint.getTarget().getClass()));
    }

    /**
     * 记录调用时间
     * 
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("apiPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            Long end = System.currentTimeMillis();
            log.info("接口响应:{} ms!", (end - start));
        }
    }
}
