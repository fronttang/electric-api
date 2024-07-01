package com.rosenzest.base.util;

import java.util.function.Function;

import org.springframework.beans.BeanUtils;

import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

/**
 * function 工具类
 * 
 * @author fronttang
 * @date 2021/07/16
 */
public final class FunctionUtils {

    /**
     * 获取对象 copy function
     * 
     * @param <T>
     * @param <R>
     * @param targetType
     * @return
     */
    public static <T, R> Function<T, R> getCopyPropertiesFunction(Class<R> targetType) {

        return new Function<T, R>() {

            @Override
            public R apply(T source) {
                try {
                    if (ObjectUtil.isNotNull(source)) {
                        final R target = ReflectUtil.newInstanceIfPossible(targetType);
                        BeanUtils.copyProperties(source, target);
                        return target;
                    }
                    return null;
                } catch (Exception e) {
                    throw new BusinessException(ResultEnum.SYSTEM_ERROR);
                }
            }
        };

    }

}
