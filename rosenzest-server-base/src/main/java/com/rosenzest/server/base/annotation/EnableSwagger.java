package com.rosenzest.server.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Indexed;

import com.rosenzest.server.base.config.SwaggerConfiguration;
import com.rosenzest.server.base.properties.SwaggerProperties;

/**
 * 开启swagger注解
 * 
 * @author fronttang
 * @date 2021/07/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Indexed
@EnableConfigurationProperties(SwaggerProperties.class)
@Import(SwaggerConfiguration.class)
public @interface EnableSwagger {

}
