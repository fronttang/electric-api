package com.rosenzest.model.base.util;

import java.lang.invoke.SerializedLambda;
import java.util.Map;

import org.apache.ibatis.reflection.property.PropertyNamer;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * @author fronttang
 * @date 2021/07/16
 */
public final class ModelColumnUtils {

    public static <T> String columnToString(SFunction<T, ?> column) {
        return columnToString(column, true);
    }

    public static <T> String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
        return getColumn(column, onlyColumn);
    }

    /**
     * 获取 SerializedLambda 对应的列信息，从 lambda 表达式中推测实体类
     * <p>
     * 如果获取不到列信息，那么本次条件组装将会失败
     *
     * @param lambda
     *            lambda 表达式
     * @param onlyColumn
     *            如果是，结果: "name", 如果否： "name" as "name"
     * @return 列
     * @throws com.baomidou.mybatisplus.core.exceptions.MybatisPlusException
     *             获取不到列信息时抛出异常
     * @see SerializedLambda#getImplClass()
     * @see SerializedLambda#getImplMethodName()
     */
    public static <T> String getColumn(SFunction<T, ?> column, boolean onlyColumn) {
        LambdaMeta meta = LambdaUtils.extract(column);
        String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
        ColumnCache columnCache = getColumnCache(fieldName, meta.getInstantiatedClass());
        return onlyColumn ? columnCache.getColumn() : columnCache.getColumnSelect();
    }

    public static ColumnCache getColumnCache(String fieldName, Class<?> lambdaClass) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(lambdaClass);
        ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(fieldName));
        Assert.notNull(columnCache, "can not find lambda cache for this property [%s] of entity [%s]", fieldName,
            lambdaClass.getName());
        return columnCache;
    }
}
