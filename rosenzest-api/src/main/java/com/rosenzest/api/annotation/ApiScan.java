/**
 * 
 */
package com.rosenzest.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.rosenzest.api.ApiScannerRegistrar;

/**
 * Use this annotation to register API impl when using Java Config.
 * 
 * API扫描注册
 * 
 * @author fronttang
 * @date 2021/09/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ApiScannerRegistrar.class)
@Repeatable(ApiScans.class)
public @interface ApiScan {

    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @ApiScan("org.my.pkg")} instead of {@code @ApiScan(basePackages = "org.my.pkg"})}.
     *
     * @return base package names
     */
    String[] value() default {};

    /**
     * Base packages to scan for API impl
     *
     * @return base package names for scanning API impl
     */
    String[] basePackages() default {};
}
