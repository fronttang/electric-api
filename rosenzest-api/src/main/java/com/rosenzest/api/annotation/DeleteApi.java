/**
 * 
 */
package com.rosenzest.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpMethod;

/**
 * 用于标记这是一个api
 * 
 * @author fronttang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Api(method = HttpMethod.DELETE)
public @interface DeleteApi {

    /**
     * API HOST
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String host() default "";

    /**
     * API 路径
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String path() default "";

    /**
     * API 编号
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String code() default "";

    /**
     * API名称
     * 
     * @return
     */
    @AliasFor(annotation = Api.class)
    String name() default "";
}
