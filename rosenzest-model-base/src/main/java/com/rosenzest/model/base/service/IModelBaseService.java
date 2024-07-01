package com.rosenzest.model.base.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rosenzest.model.base.metadata.LambdaOrderItem;
import com.rosenzest.model.base.query.ExtendLambdaQueryChainWrapper;
import com.rosenzest.model.base.query.ExtendQueryChainWrapper;
import com.rosenzest.model.base.util.ExtendChainWrappers;
import com.rosenzest.model.base.util.ModelFunctionUtils;

import cn.hutool.core.util.ObjectUtil;

/**
 * @author fronttang
 * @date 2021/07/15
 */
public interface IModelBaseService<T> extends IService<T> {

    /**
     * 根据指定字段查询单条记录 排序
     * 
     * @param column
     * @param val
     * @param orderBys
     * @return
     */
    T getOne(SFunction<T, ?> column, Object val);

    default T getOne(SFunction<T, ?> column, Object val, LambdaOrderItem<T> orderBy) {
        return getOne(column, val, Arrays.asList(orderBy));
    }

    T getOne(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys);

    default <R> R getOne(SFunction<T, ?> column, Object val, Class<R> clazz) {
        return getOne(column, val, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    <R> R getOne(SFunction<T, ?> column, Object val, Function<T, R> mapper);

    default <R> R getOne(SFunction<T, ?> column, Object val, LambdaOrderItem<T> orderBy, Class<R> clazz) {
        return getOne(column, val, orderBy, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    default <R> R getOne(SFunction<T, ?> column, Object val, LambdaOrderItem<T> orderBy, Function<T, R> mapper) {
        return getOne(column, val, Arrays.asList(orderBy), mapper);
    }

    default <R> R getOne(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys, Class<R> clazz) {
        return getOne(column, val, orderBys, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    <R> R getOne(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys, Function<T, R> mapper);

    T getOne(String column, Object val, OrderItem... orderBys);

    <R> R getOne(String column, Object val, Function<T, R> mapper, OrderItem... orderBys);

    default <R> R getOne(String column, Object val, Class<R> clazz, OrderItem... orderBys) {
        return getOne(column, val, ModelFunctionUtils.getModelCopyFunction(clazz), orderBys);
    }

    default <R> R getOne(Wrapper<T> queryWrapper, Class<R> clazz) {
        return getOne(queryWrapper, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    <R> R getOne(Wrapper<T> queryWrapper, Function<T, R> mapper);

    /**
     * 根据字段查询集合 排序
     * 
     * @param column
     * @param val
     * @return
     */
    List<T> list(SFunction<T, ?> column, Object val);

    default List<T> list(SFunction<T, ?> column, Object val, LambdaOrderItem<T> orderBy) {
        return list(column, val, Arrays.asList(orderBy));
    }

    List<T> list(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys);

    default <R> List<R> list(SFunction<T, ?> column, Object val, Class<R> clazz) {
        return list(column, val, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    <R> List<R> list(SFunction<T, ?> column, Object val, Function<T, R> mapper);

    default <R> List<R> list(SFunction<T, ?> column, Object val, LambdaOrderItem<T> orderBy, Class<R> clazz) {
        return list(column, val, orderBy, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    default <R> List<R> list(SFunction<T, ?> column, Object val, LambdaOrderItem<T> orderBy, Function<T, R> mapper) {
        return list(column, val, Arrays.asList(orderBy), mapper);
    }

    default <R> List<R> list(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys, Class<R> clazz) {
        return list(column, val, orderBys, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    <R> List<R> list(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys, Function<T, R> mapper);

    List<T> list(String column, Object val, OrderItem... orderBys);

    <R> List<R> list(String column, Object val, Function<T, R> mapper, OrderItem... orderBys);

    default <R> List<R> list(String column, Object val, Class<R> clazz, OrderItem... orderBys) {
        return list(column, val, ModelFunctionUtils.getModelCopyFunction(clazz), orderBys);
    }

    default <R> List<R> list(Wrapper<T> queryWrapper, Class<R> clazz) {
        return list(queryWrapper, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    default <R> List<R> list(Class<R> clazz) {
        return list(Wrappers.emptyWrapper(), clazz);
    }

    default <R> List<R> list(Wrapper<T> queryWrapper, Function<T, R> mapper) {
        return getBaseMapper().selectList(queryWrapper).stream().filter(Objects::nonNull).map(mapper)
            .filter(Objects::nonNull).collect(Collectors.toList());
    }

    default <R> R getById(Serializable id, Class<R> clazz) {
        return getById(id, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    default <R> R getById(Serializable id, Function<T, R> mapper) {
        T t = getById(id);
        if (ObjectUtil.isNotNull(t)) {
            return mapper.apply(t);
        }
        return null;
    }

    /**
     * 翻页查询
     */
    default <E extends IPage<T>, R> IPage<R> page(E page, Wrapper<T> queryWrapper, Class<R> clazz) {

        return page(page, queryWrapper, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    default <E extends IPage<T>, R> IPage<R> page(E page, Class<R> clazz) {

        return page(page, Wrappers.emptyWrapper(), clazz);
    }

    default <E extends IPage<T>, R> IPage<R> page(E page, Wrapper<T> queryWrapper, Function<T, R> mapper) {

        page = getBaseMapper().selectPage(page, Wrappers.emptyWrapper());

        IPage<R> resultPage = page.convert(mapper);

        return resultPage;
    }

    /**
     * 链式查询 普通
     *
     * @return QueryWrapper 的包装类
     */
    default ExtendQueryChainWrapper<T> extendQuery() {
        return ExtendChainWrappers.queryChain(getBaseMapper());
    }

    /**
     * 链式查询 lambda 式
     * <p>
     * 注意：不支持 Kotlin
     * </p>
     *
     * @return LambdaQueryWrapper 的包装类
     */
    default ExtendLambdaQueryChainWrapper<T> extendLambdaQuery() {
        return ExtendChainWrappers.lambdaQueryChain(getBaseMapper());
    }

}
