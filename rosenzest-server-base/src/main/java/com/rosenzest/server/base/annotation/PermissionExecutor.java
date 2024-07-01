/**
 * 
 */
package com.rosenzest.server.base.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 权限检查执行器注解，用于标记这是一个权限检查执行器
 * 
 * @author fronttang
 */
@Inherited
@Retention(RUNTIME)
@Target(TYPE)
@Component
public @interface PermissionExecutor {

}
