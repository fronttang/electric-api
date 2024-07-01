package com.rosenzest.server.base.aspect;

import java.lang.reflect.Method;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;
import com.rosenzest.server.base.annotation.Permission;
import com.rosenzest.server.base.permission.IPermissionExecutor;

/**
 * 权限过滤Aop切面
 * 
 * @author fronttang
 * @date 2021/07/28
 */
@Aspect
@Component
public class PermissionAspect implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 权限切入点
     */
    @Pointcut("@annotation(com.rosenzest.server.base.annotation.Permission) || @within(com.rosenzest.server.base.annotation.Permission)")
    private void getPermissionPointCut() {}

    /**
     * 执行权限过滤
     */
    @Before("getPermissionPointCut()")
    public void doPermission(JoinPoint joinPoint) {
        Permission permission = getPermissionAnnotation(joinPoint);
        if (permission != null) {
            if (Objects.nonNull(permission.type())) {
                IPermissionExecutor permissionExecutor = applicationContext.getBean(permission.type());
                if (Objects.nonNull(permissionExecutor)) {
                    if (!permissionExecutor.execute(permission, joinPoint)) {
                        throw new BusinessException(ResultEnum.FORBIDDEN);
                    }
                }
            }
        }
    }

    private Permission getPermissionAnnotation(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Permission permission = AnnotatedElementUtils.findMergedAnnotation(method, Permission.class);
        if (Objects.isNull(permission)) {
            permission = AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), Permission.class);
        }
        // Permission permission = XmzyAnnotationUtils.findAnnotationAndDeclaringAnnotation(method, Permission.class);
        return permission;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PermissionAspect.applicationContext = applicationContext;
    }

}
