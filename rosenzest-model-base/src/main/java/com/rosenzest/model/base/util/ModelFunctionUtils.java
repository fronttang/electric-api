package com.rosenzest.model.base.util;

import java.util.function.Function;

import com.rosenzest.base.util.FunctionUtils;

/**
 * @author fronttang
 * @date 2021/07/16
 */
public final class ModelFunctionUtils {

    /**
     * 获取对象copy function
     * 
     * @param <T>
     * @param <R>
     * @param targetType
     * @return
     */
    public static <T, R> Function<T, R> getModelCopyFunction(Class<R> targetType) {
        return FunctionUtils.getCopyPropertiesFunction(targetType);
    }

}
