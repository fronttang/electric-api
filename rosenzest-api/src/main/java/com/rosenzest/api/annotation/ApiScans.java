/**
 * 
 */
package com.rosenzest.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.rosenzest.api.ApiScannerRegistrar;

/**
 * The Container annotation that aggregates several {@link ApiScan} annotations.
 * 
 * API扫描注册
 * 
 * @author fronttang
 * @date 2021/09/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ApiScannerRegistrar.RepeatingRegistrar.class)
public @interface ApiScans {

    ApiScan[] value();
}
