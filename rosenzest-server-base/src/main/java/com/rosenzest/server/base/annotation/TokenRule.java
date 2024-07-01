/**
 * 
 */
package com.rosenzest.server.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * token 验证规则
 * 
 * @author fronttang
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TokenRule {

    /**
     * 是否必须带token </br>
     * 为false时则请求头不需要带token,不会进行任何验证
     * 
     * @return
     */
    boolean required() default true;

    /**
     * 是否需要登录</br>
     * 加密开启但不需要登录，则不会验证登录信息，用于一些加密但不需要登录的接口
     * 
     * @return
     */
    boolean login() default true;

    /**
     * 是否必须加密 </br>
     * 为false时不会验证token加密，
     * 
     * @return
     */
    boolean security() default true;
}
