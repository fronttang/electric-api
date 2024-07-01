/**
 * 
 */
package com.rosenzest.server.base.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.rosenzest.server.base.enums.RequestParamType;

/**
 * 权限检查参数
 * 
 * @author fronttang
 */
@Inherited
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface PermissionParam {

    /**
     * 参数名
     * 
     * @return
     */
    String name() default "custNo";

    /**
     * 参数类型
     * 
     * @return
     */
    RequestParamType type() default RequestParamType.PATH_VARIABLE;
}
