package com.rosenzest.base.sensitive;

import com.rosenzest.base.sensitive.handler.RegexpSensitiveHandler;

import cn.hutool.core.util.ReflectUtil;

/**
 * 脱敏工具类
 * 
 * @author fronttang
 * @date 2021/08/02
 */
public final class SensitiveUtil {

    /**
     * 
     * @param source
     * @param pattern
     * @param target
     * @param handler
     * @return
     */
    public static String handleSensitive(String source, String pattern, String replace,
        Class<? extends ISensitiveHandler> handlerType) {

        ISensitiveHandler handler = null;

        if (handlerType != null) {
            if (handlerType.isAssignableFrom(RegexpSensitiveHandler.class)) {
                handler = new RegexpSensitiveHandler(pattern, replace);
            } else {
                handler = ReflectUtil.newInstanceIfPossible(handlerType);
            }
            if (handler != null) {
                return handler.handle(source);
            }
        }
        return source;
    }

    /**
     * 
     * @param source
     * @param wapper
     * @return
     */
    public static String handleSensitive(String source, SensitiveWapper wapper) {
        return handleSensitive(source, wapper.getPattern(), wapper.getReplace(), wapper.getHandler());
    }
}
