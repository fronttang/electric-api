package com.rosenzest.base.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


import cn.hutool.core.bean.BeanUtil;

/**
 * BeanUtils 工具类
 * 
 * @author fronttang
 * @date 2021/07/03
 */
public final class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 复制集合中的Bean属性<br>
     * 此方法遍历集合中每个Bean，复制其属性后加入一个新的{@link List}中。
     * 
     * @param <T>
     * @param source
     * @param clazz
     * @return
     */
    public static <T> List<T> copyList(Collection<?> collection, Class<T> targetType) {
        return BeanUtil.copyToList(collection, targetType);
    }

    /**
     * 复制集合中的Bean属性<br>
     * 此方法遍历集合中每个Bean，复制其属性后加入一个新的{@link List}中。
     * 
     * @param <T>
     * @param <R>
     * @param collection
     * @param mapper
     * @return
     */
    public static <T, R> List<R> copyList(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }
}
