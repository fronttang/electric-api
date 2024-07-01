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

import com.rosenzest.api.IApi;

/**
 * 用于标记这是一个api工厂
 * 
 * @author fronttang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiFactory {

    /**
     * API 接口实现类
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    @AliasFor("exec")
    Class<? extends IApi> value() default IApi.class;

    /**
     * API 接口实现类
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    @AliasFor("value")
    Class<? extends IApi> exec() default IApi.class;
}
