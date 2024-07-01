/**
 * 
 */
package com.rosenzest.base.constant;

/**
 * SpringSecurity相关常量
 * 
 * @author fronttang
 */
public interface SpringSecurityConstant {

    /**
     * 放开权限校验的接口
     */
    String[] NONE_SECURITY_URL_PATTERNS = {

        // 前端的
        "/favicon.ico",

        // swagger相关的
        "/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs", "/v2/api-docs-ext",

        // 后端的
        "/", "/login", "/logout", "/captcha", "/error"};

    /**
     * 放开xss过滤的接口
     */
    String[] UN_XSS_FILTER_URL = {};
}
