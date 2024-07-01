/**
 * 
 */
package com.rosenzest.server.base.permission;

import org.aspectj.lang.JoinPoint;

import com.rosenzest.server.base.annotation.Permission;

/**
 * 权限检查接口，
 * 
 * @author fronttang
 */
public interface IPermissionExecutor {

    /**
     * 执行权限检查方法
     * 
     * @param permission
     *            权限检查注解
     * @param joinPoint
     *            权限点,可以取请求参数
     * @return true 为检查通过 ,false 为检查不过抛出没有权限业务异常
     */
    boolean execute(Permission permission, JoinPoint joinPoint);
}
