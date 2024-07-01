package com.rosenzest.server.base.cache;

import com.rosenzest.base.constant.CacheConstants;
import com.rosenzest.base.enums.TerminalType;

import cn.hutool.core.util.StrUtil;

/**
 * @author fronttang
 * @date 2021/07/21
 */
public class CacheKeyBuilder {

    /**
     * 构建客户信息缓存key
     * 
     * @param custNo
     * @return
     */
    public static String getCustInfoKey(Long custNo) {
        String key = StrUtil.format("{}:{}", CacheConstants.CUST_INFO_KEY, custNo);
        return key;
    }

    /**
     * 构建客户token缓存key
     * 
     * @param custNo
     * @return
     */
    public static String getCustTokenKey(Long userId) {
        String key = StrUtil.format("{}:{}", CacheConstants.CUST_TOKEN_KEY, userId);
        return key;
    }

    /**
     * 构建客户 APP端 token缓存key
     * 
     * @param custNo
     * @return
     */
    public static String getCustAPPTokenKey(Long userId) {
        return getCustTokenKey(TerminalType.APP.getName(), userId);
    }

    /**
     * 构建客户token缓存key
     * 
     * @param terminal
     * @param custNo
     * @return
     */
    public static String getCustTokenKey(String terminal, Long userId) {
        String key = StrUtil.format("{}:{}:{}", CacheConstants.CUST_TOKEN_KEY, terminal, userId);
        return key;
    }

    /**
     * 构建系统用户token缓存key
     * 
     * @param custNo
     * @return
     */
    public static String getSysUserTokenKey(String userId) {
        String key = StrUtil.format("{}:{}", CacheConstants.SYS_USER_TOKEN_KEY, userId);
        return key;
    }

    /**
     * 构建系统用户后台用户权限缓存key
     * 
     * @param userId
     * @return
     */
    public static String getSysUserSecurityKey(String userId) {
        String key = StrUtil.format("{}:{}", CacheConstants.SYS_USER_SECURITY_KEY, userId);
        return key;
    }
}
