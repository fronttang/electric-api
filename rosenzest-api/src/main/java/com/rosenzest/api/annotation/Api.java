/**
 * 
 */
package com.rosenzest.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.HttpMethod;


/**
 * 用于标记这是一个api
 * 
 * @author fronttang
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {

    /**
     * API HOST
     * 
     * @return
     */
    String host() default "";

    /**
     * API 路径
     * 
     * @return
     */
    String path() default "";

    /**
     * API 编号
     * 
     * @return
     */
    String code() default "";

    /**
     * API名称
     * 
     * @return
     */
    String name() default "";

    /**
     * API 请求方式
     * 
     * @return
     */
    HttpMethod method() default HttpMethod.POST;
}
