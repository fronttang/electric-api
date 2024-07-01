package com.rosenzest.base.sensitive.handler;

import com.rosenzest.base.sensitive.ISensitiveHandler;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 正则替换脱敏处理器实现
 * 
 * @author fronttang
 * @date 2021/08/02
 */
@Data
@Slf4j
public class RegexpSensitiveHandler implements ISensitiveHandler {

    /**
     * 正则
     */
    private String pattern;

    /**
     * 正则替换的内容
     */
    private String replace = "*";

    public RegexpSensitiveHandler(String pattern, String replace) {
        super();
        this.pattern = pattern;
        this.replace = replace;
    }

    @Override
    public String handle(String source) {
        try {

            if (StrUtil.isBlank(pattern)) {
                return source;
            }

            return source.replaceAll(this.pattern, this.replace);

        } catch (Exception e) {
            log.error("", e);
            return source;
        }
    }
}
