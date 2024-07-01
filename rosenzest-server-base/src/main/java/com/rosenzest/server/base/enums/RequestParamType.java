/**
 * 
 */
package com.rosenzest.server.base.enums;

/**
 * 请求参数类型
 * 
 * @author fronttang
 */
public enum RequestParamType {

    /**
     * RequestParam 请求参数 {@link org.springframework.web.bind.annotation.RequestParam}
     */
    REQUEST_PARAM,

    /**
     * PathVariable 请求参数{@link org.springframework.web.bind.annotation.PathVariable}
     */
    PATH_VARIABLE,

    /**
     * RequestBody 请求参数{@link org.springframework.web.bind.annotation.RequestBody}
     */
    REQUEST_BODY
}
