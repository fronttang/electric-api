package com.rosenzest.model.base.query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.ChainQuery;
import com.rosenzest.model.base.util.ModelFunctionUtils;

import cn.hutool.core.util.ObjectUtil;

/**
 * 扩展mybatis plus ChainQuery
 * 
 * @author fronttang
 * @date 2021/07/20
 */
public interface ExtendChainQuery<T> extends ChainQuery<T> {

    /**
     * 获取集合
     *
     * @return 集合
     */
    default <R> List<R> list(Function<T, R> mapper) {
        return getBaseMapper().selectList(getWrapper()).stream().filter(Objects::nonNull).map(mapper)
            .filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 获取集合
     * 
     * @return 集合
     */
    default <R> List<R> list(Class<R> clazz) {
        return list(ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    /**
     * 获取单个
     *
     * @return 单个
     */
    default <R> R one(Function<T, R> mapper) {
        T t = getBaseMapper().selectOne(getWrapper());
        if (ObjectUtil.isNotNull(t)) {
            return mapper.apply(t);
        }
        return null;
    }

    /**
     * 获取单个
     *
     * @return 单个
     */
    default <R> R one(Class<R> clazz) {
        return one(ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    /**
     * 获取单个
     *
     * @return 单个
     * @since 3.3.0
     */
    default <R> Optional<R> oneOpt(Function<T, R> mapper) {
        return Optional.ofNullable(one(mapper));
    }

    /**
     * 获取单个
     *
     * @return 单个
     * @since 3.3.0
     */
    default <R> Optional<R> oneOpt(Class<R> clazz) {
        return oneOpt(ModelFunctionUtils.getModelCopyFunction(clazz));
    }

    /**
     * 获取分页数据
     *
     * @param page
     *            分页条件
     * @return 分页数据
     */
    default <R, E extends IPage<T>> IPage<R> page(E page, Function<T, R> mapper) {
        return getBaseMapper().selectPage(page, getWrapper()).convert(mapper);
    }

    /**
     * 获取分页数据
     * 
     * @param <R>
     * @param <E>
     * @param page
     * @param clazz
     * @return
     */
    default <R, E extends IPage<T>> IPage<R> page(E page, Class<R> clazz) {
        return page(page, ModelFunctionUtils.getModelCopyFunction(clazz));
    }

}
