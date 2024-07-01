package com.rosenzest.base.util;

import java.time.LocalDateTime;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * ID生成工具类
 * 
 * @author fronttang
 * @date 2021/09/18
 */
public class IdUtils extends IdUtil {

    /**
     * 请求号
     * 
     * @return
     */
    public static String requestNo() {
        LocalDateTime now = LocalDateTime.now();
        String requestTime = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
        String requestNo = requestTime + RandomUtil.randomNumbers(6);
        return requestNo;
    }
}
