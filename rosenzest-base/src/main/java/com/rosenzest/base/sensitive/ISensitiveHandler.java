package com.rosenzest.base.sensitive;

/**
 * 脱敏处理器接口
 * 
 * @author fronttang
 * @date 2021/08/02
 */
public interface ISensitiveHandler {

    /**
     * 执行脱敏处理逻辑
     * 
     * @param source
     * @return
     */
    String handle(String source);

    /**
     * 是否能处理
     * 
     * @param source
     * @return
     */
    default boolean canHandle(String source) {
        return Boolean.TRUE;
    }
}
