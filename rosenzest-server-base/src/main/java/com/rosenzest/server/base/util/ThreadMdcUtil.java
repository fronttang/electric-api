package com.rosenzest.server.base.util;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.MDC;

import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.base.util.UniCodeUtil;

import cn.hutool.core.util.StrUtil;

/**
 * 
 * @author fronttang
 * @date 2021/07/29
 */
public final class ThreadMdcUtil {

    public static void setTraceIdIfAbsent() {
        String trace = MDC.get(SystemConstants.REQUEST_NO_HEADER_NAME);
        if (StrUtil.isBlank(trace)) {
            MDC.put(SystemConstants.REQUEST_NO_HEADER_NAME, UniCodeUtil.rand(32));
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
