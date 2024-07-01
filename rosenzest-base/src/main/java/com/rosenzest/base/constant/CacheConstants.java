/** */
package com.rosenzest.base.constant;

import com.rosenzest.base.sensitive.ISensitiveHandler;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;

/**
 * 缓存相关常量
 * 
 * @author fronttang
 */
public interface CacheConstants {

    /**
     * 二维码过期时间 120秒
     */
    Integer QRCODE_EXPIRE_TIME = 120;

    /**
     * 手机验证码过期时间 120秒
     */
    Integer MOBILE_CODE_EXPIRE_TIME = 120;

    /**
     * 客户token redis 储存key
     */
    String CUST_TOKEN_KEY = "CUST:TOKEN";

    /**
     * 后台用户token
     */
    String SYS_USER_TOKEN_KEY = "SYS:USER:TOKEN";

    /**
     * 后台用户权限
     */
    String SYS_USER_SECURITY_KEY = "SYS:USER:SECURITY";

    /**
     * 客户信息 redis 储存key
     */
    String CUST_INFO_KEY = "CUST:INFO";

    /**
     * 
     */
    String SENSITIVE_WAPPER_KEY = "SENSITIVE:WAPPER";

    /**
     * 获取脱敏信息缓存key
     * 
     * @param province
     * @return
     */
    static String getSensitiveWapperKey(String pattern, String replace, Class<? extends ISensitiveHandler> handler) {

        pattern = Base64.encode(pattern);
        replace = Base64.encode(replace);
        String handlerBase64 = Base64.encode(String.valueOf(handler.hashCode()));

        String cacheKey =
            StrUtil.format("{}:{}{}{}", CacheConstants.SENSITIVE_WAPPER_KEY, pattern, replace, handlerBase64);
        return cacheKey;
    }

}
