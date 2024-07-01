package com.rosenzest.base.sensitive;

import java.util.concurrent.ConcurrentHashMap;

import com.rosenzest.base.annotation.Sensitive;
import com.rosenzest.base.constant.CacheConstants;
import com.rosenzest.base.sensitive.handler.RegexpSensitiveHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 脱敏信息包装类
 * 
 * @author fronttang
 * @date 2021/08/02
 */
@Data
@AllArgsConstructor
public class SensitiveWapper implements ISensitive {

    private static final ConcurrentHashMap<String, SensitiveWapper> CACHE =
        new ConcurrentHashMap<String, SensitiveWapper>();

    /**
     * 正则
     */
    private String pattern;

    /**
     * 正则替换的内容
     */
    private String replace;

    /**
     * 处理器
     */
    private Class<? extends ISensitiveHandler> handler = RegexpSensitiveHandler.class;

    public SensitiveWapper(String pattern, String replace) {
        super();
        this.pattern = pattern;
        this.replace = replace;
    }

    /**
     * 将注解成构建成SensitiveWapper对象
     * 
     * @param sensitive
     * @return
     */
    public static SensitiveWapper build(Sensitive sensitive) {
        SensitiveType type = sensitive.type();
        if (type != null) {
            return type.toWapper();
        } else {
            return build(sensitive.pattern(), sensitive.replace(), sensitive.handler());
        }
    }

    /**
     * 构建SensitiveWapper对象
     * 
     * @param sensitive
     * @return
     */
    public static SensitiveWapper build(ISensitive sensitive) {
        if (sensitive == null) {
            return null;
        } else {
            return build(sensitive.getPattern(), sensitive.getReplace(), sensitive.getHandler());
        }
    }

    /**
     * 构建SensitiveWapper对象
     * 
     * @param pattern
     * @param replace
     * @param handler
     * @return
     */
    public static SensitiveWapper build(String pattern, String replace, Class<? extends ISensitiveHandler> handler) {
        String cacheKey = CacheConstants.getSensitiveWapperKey(pattern, replace, handler);
        SensitiveWapper wapper = CACHE.get(cacheKey);
        if (wapper == null) {
            wapper = new SensitiveWapper(pattern, replace, handler);
            CACHE.put(cacheKey, wapper);
        }
        return wapper;
    }

    @Override
    public SensitiveWapper toWapper() {
        return this;
    }

}
