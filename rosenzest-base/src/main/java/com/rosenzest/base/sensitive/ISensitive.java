package com.rosenzest.base.sensitive;

import com.rosenzest.base.sensitive.handler.RegexpSensitiveHandler;

/**
 * 抽象脱敏定义
 * 
 * @author fronttang
 * @date 2021/08/02
 */
public interface ISensitive {

    /**
     * 正则表达式
     * 
     * @return
     */
    String getPattern();

    /**
     * 替换内容
     * 
     * @return
     */
    String getReplace();

    /**
     * 处理器
     * 
     * @return
     */
    default Class<? extends ISensitiveHandler> getHandler() {
        return RegexpSensitiveHandler.class;
    }

    /**
     * 生成包装信息
     * 
     * @return
     */
    default SensitiveWapper toWapper() {
        return SensitiveWapper.build(this);
    }
}
