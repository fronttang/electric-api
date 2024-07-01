package com.rosenzest.server.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.rosenzest.server.base.permission.IPermissionExecutor;

/**
 * 权限注解，用于检查权限 使用方式：@Permission表示检查是否有权限访问该资源
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Permission {

    /**
     * 权限检查执行器
     * 
     * @return
     */
    @AliasFor("type")
    Class<? extends IPermissionExecutor> value() default IPermissionExecutor.class;

    /**
     * 权限检查执行器
     * 
     * @return
     */
    @AliasFor("value")
    Class<? extends IPermissionExecutor> type() default IPermissionExecutor.class;

    /**
     * 权限检查请求参数
     * 
     * @return
     */
    PermissionParam[] param() default {@PermissionParam};
}
