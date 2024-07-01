package com.rosenzest.model.base.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.rosenzest.model.base.mapper.ModelBaseMapper;
import com.rosenzest.model.base.metadata.LambdaOrderItem;

import cn.hutool.core.collection.CollUtil;

/**
 * @author fronttang
 * @date 2021/07/15
 */
@SuppressWarnings("unchecked")
public class ModelBaseServiceImpl<M extends ModelBaseMapper<T>, T> extends ServiceImpl<M, T>
    implements IModelBaseService<T> {

    protected Log log = LogFactory.getLog(getClass());

    @Override
    protected Class<T> currentMapperClass() {
        // (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 0);
        return (Class<T>)this.getResolvableType().as(ModelBaseServiceImpl.class).getGeneric(0).getType();
    }

    @Override
    protected Class<T> currentModelClass() {
        // (Class<T>)ReflectionKit.getSuperClassGenericType(getClass(), 1);
        return (Class<T>)this.getResolvableType().as(ModelBaseServiceImpl.class).getGeneric(1).getType();
    }

    /**
     * 根据指定字段查询单条记录
     * 
     * @param column
     * @param val
     * @return
     */
    @Override
    public T getOne(SFunction<T, ?> column, Object val) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapperLimit(column, val);

        return this.getOne(queryWrapper);
    }

    @Override
    public T getOne(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapperLimit(column, val, orderBys);

        return this.getOne(queryWrapper);
    }

    @Override
    public <R> R getOne(SFunction<T, ?> column, Object val, Function<T, R> mapper) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapperLimit(column, val);

        return this.getOne(queryWrapper, mapper);
    }

    @Override
    public <R> R getOne(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys, Function<T, R> mapper) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapperLimit(column, val, orderBys);

        return this.getOne(queryWrapper, mapper);
    }

    @Override
    public T getOne(String column, Object val, OrderItem... orderBys) {

        QueryWrapper<T> queryWrapper = buildQueryWrapperLimit(column, val, orderBys);

        return this.getOne(queryWrapper);
    }

    @Override
    public <R> R getOne(String column, Object val, Function<T, R> mapper, OrderItem... orderBys) {

        QueryWrapper<T> queryWrapper = buildQueryWrapperLimit(column, val, orderBys);

        return this.getOne(queryWrapper, mapper);
    }

    @Override
    public <R> R getOne(Wrapper<T> queryWrapper, Function<T, R> mapper) {

        List<R> list = list(queryWrapper, mapper);

        return SqlHelper.getObject(log, list);
    }

    /**
     * 根据字段查询集合
     * 
     * @param column
     * @param val
     * @return
     */
    @Override
    public List<T> list(SFunction<T, ?> column, Object val) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapper(column, val);

        return this.list(queryWrapper);
    }

    @Override
    public List<T> list(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapper(column, val, orderBys);

        return this.list(queryWrapper);
    }

    @Override
    public <R> List<R> list(SFunction<T, ?> column, Object val, Function<T, R> mapper) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapper(column, val);

        return this.list(queryWrapper, mapper);
    }

    @Override
    public <R> List<R> list(SFunction<T, ?> column, Object val, List<LambdaOrderItem<T>> orderBys,
        Function<T, R> mapper) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapper(column, val, orderBys);

        return this.list(queryWrapper, mapper);
    }

    @Override
    public List<T> list(String column, Object val, OrderItem... orderBys) {

        QueryWrapper<T> queryWrapper = buildQueryWrapper(column, val, orderBys);

        return this.list(queryWrapper);
    }

    @Override
    public <R> List<R> list(String column, Object val, Function<T, R> mapper, OrderItem... orderBys) {

        QueryWrapper<T> queryWrapper = buildQueryWrapper(column, val, orderBys);

        return this.list(queryWrapper, mapper);
    }

    private LambdaQueryWrapper<T> buildLambdaQueryWrapper(SFunction<T, ?> column, Object val,
        List<LambdaOrderItem<T>> orderBys) {

        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<T>();
        queryWrapper.eq(column, val);

        if (CollUtil.isNotEmpty(orderBys)) {
            orderBys.forEach((orderItem) -> {
                queryWrapper.orderBy(true, orderItem.isAsc(), orderItem.getColumn());
            });
        }

        return queryWrapper;
    }

    private LambdaQueryWrapper<T> buildLambdaQueryWrapper(SFunction<T, ?> column, Object val) {
        return buildLambdaQueryWrapper(column, val, null);
    }

    private LambdaQueryWrapper<T> buildLambdaQueryWrapperLimit(SFunction<T, ?> column, Object val,
        List<LambdaOrderItem<T>> orderBys) {

        LambdaQueryWrapper<T> queryWrapper = buildLambdaQueryWrapper(column, val, orderBys);

        queryWrapper.last(" LIMIT 1 ");

        return queryWrapper;
    }

    private LambdaQueryWrapper<T> buildLambdaQueryWrapperLimit(SFunction<T, ?> column, Object val) {

        return buildLambdaQueryWrapperLimit(column, val, null);
    }

    private QueryWrapper<T> buildQueryWrapper(String column, Object val, OrderItem... orderBys) {

        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        queryWrapper.eq(column, val);

        Arrays.asList(orderBys).forEach((orderItem) -> {
            queryWrapper.orderBy(true, orderItem.isAsc(), orderItem.getColumn());
        });
        return queryWrapper;
    }

    private QueryWrapper<T> buildQueryWrapperLimit(String column, Object val, OrderItem... orderBys) {
        QueryWrapper<T> queryWrapper = buildQueryWrapper(column, val, orderBys);

        queryWrapper.last(" LIMIT 1 ");
        return queryWrapper;
    }

}
