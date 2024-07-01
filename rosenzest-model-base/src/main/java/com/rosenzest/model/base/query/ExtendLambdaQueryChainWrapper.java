package com.rosenzest.model.base.query;

import java.util.function.Predicate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;

/**
 * 扩展 Mybatis-Plus lambdaQuery
 * 
 * @author fronttang
 * @date 2021/07/20
 */
public class ExtendLambdaQueryChainWrapper<T>
    extends AbstractChainWrapper<T, SFunction<T, ?>, ExtendLambdaQueryChainWrapper<T>, LambdaQueryWrapper<T>>
    implements ExtendChainQuery<T>, Query<ExtendLambdaQueryChainWrapper<T>, T, SFunction<T, ?>> {

    private final BaseMapper<T> baseMapper;

    /**
     *
     */
    private static final long serialVersionUID = 5237736778477159671L;

    public ExtendLambdaQueryChainWrapper(BaseMapper<T> baseMapper) {
        super();
        this.baseMapper = baseMapper;
        super.wrapperChildren = new LambdaQueryWrapper<>();
    }

    @Override
    public String getSqlSelect() {
        throw ExceptionUtils.mpe("can not use this method for \"%s\"", "getSqlSelect");
    }

    @Override
    public BaseMapper<T> getBaseMapper() {
        return baseMapper;
    }

    @Override
    @SafeVarargs
    public final ExtendLambdaQueryChainWrapper<T> select(SFunction<T, ?>... columns) {
        wrapperChildren.select(columns);
        return typedThis;
    }

    @Override
    public ExtendLambdaQueryChainWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        wrapperChildren.select(entityClass, predicate);
        return typedThis;
    }

}
