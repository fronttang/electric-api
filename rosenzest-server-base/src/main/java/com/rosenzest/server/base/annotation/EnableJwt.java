package com.rosenzest.server.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Indexed;

import com.rosenzest.server.base.config.JwtTokenConfiguration;
import com.rosenzest.server.base.properties.TokenProperties;

/**
 * @author fronttang
 * @date 2021/07/15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Indexed
@EnableConfigurationProperties(TokenProperties.class)
@Import(JwtTokenConfiguration.class)
public @interface EnableJwt {

}
