package com.rosenzest.api.enums;

import org.springframework.http.HttpMethod;

/**
 * API接口定义枚举
 * 
 * @author fronttang
 * @date 2021/08/25
 */
public interface IApiEnum {

    default String code() {
        return getCode();
    }

    default String desc() {
        return getName();
    }

    default String host() {
        return getHost();
    }

    default String path() {
        return getPath();
    }

    default HttpMethod method() {
        return getMethod();
    }

    /**
     * 接口CODE
     * 
     * @return
     */
    String getCode();

    /**
     * 接口名
     * 
     * @return
     */
    String getName();

    /**
     * 接口HOST
     * 
     * @return
     */
    String getHost();

    /**
     * 接口地址
     * 
     * @return
     */
    String getPath();

    /**
     * 请求方式
     * 
     * @return
     */
    HttpMethod getMethod();
}
