package com.rosenzest.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Indexed;

import com.rosenzest.base.config.RedisConfiguration;

/**
 * @author fronttang
 * @date 2021/07/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Indexed
@EnableCaching
@Import(RedisConfiguration.class)
public @interface EnableRedis {

}
